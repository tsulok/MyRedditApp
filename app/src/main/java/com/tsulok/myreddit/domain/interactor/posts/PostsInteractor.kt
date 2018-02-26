package com.tsulok.myreddit.domain.interactor.posts

import com.tsulok.myreddit.domain.model.PostItem
import com.tsulok.myreddit.domain.network.services.post.PostService
import com.tsulok.myreddit.domain.repository.database.models.PostItemDBModel
import com.tsulok.myreddit.domain.repository.post.IPostRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Interactor for posts
 */
interface IPostsInteractor {

    /**
     * List post from the network or from the database
     */
    fun listPosts(): Observable<List<PostItem>>
}

class PostsInteractor
@Inject constructor(private val postRepository: IPostRepository,
                    private val postService: PostService) : IPostsInteractor {
    override fun listPosts(): Observable<List<PostItem>> {
        return postService.listNewPosts("formula1")
                .doOnNext { postRepository.removeAll() }
                .map { response ->
                    response.data.children.mapNotNull {
                        if (it.data.id == null) {
                            return@mapNotNull null
                        }
                        val dbModel = PostItemDBModel(it.data.id!!,
                                it.data.title ?: "",
                                it.data.author ?: "",
                                it.data.createdDate,
                                it.data.url,
                                it.data.thumbnail)

                        // Check if preview if available and use the greatest but not the source image
                        it.data.preview?.let { preview ->
                            if (preview.enabled) {
                                preview.images?.lastOrNull()?.let {
                                    it.resolutions.lastOrNull()?.let {
                                        if (it.url != null) {
                                            dbModel.thumbnailUrl = it.url.replace("&amp;", "&")
                                        }
                                    }
                                }
                            }
                        }

                        return@mapNotNull dbModel
                    }
                }
                .doOnNext { postRepository.insertAll(it) }
                .onErrorResumeNext { throwable: Throwable ->
                    postRepository.listAllItemsRx().toObservable()
                }
                .map { items -> items.map { PostItem(it) } }
    }
}