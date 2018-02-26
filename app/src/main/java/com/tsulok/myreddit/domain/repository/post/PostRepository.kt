package com.tsulok.myreddit.domain.repository.post

import com.tsulok.myreddit.domain.repository.base.BaseRepository
import com.tsulok.myreddit.domain.repository.base.IBaseDAOMapper
import com.tsulok.myreddit.domain.repository.base.IBaseRepository
import com.tsulok.myreddit.domain.repository.database.IRealmInstanceProvider
import com.tsulok.myreddit.domain.repository.database.models.PostItemDBModel
import javax.inject.Inject

/**
 * Repository for posts
 */
interface IPostRepository : IBaseRepository<PostItemDBModel> {

}

class PostRepository
@Inject constructor(realmInstanceProvider: IRealmInstanceProvider) : BaseRepository<PostItemDBModel>(realmInstanceProvider),
        IPostRepository {
    override fun provideClass(): Class<PostItemDBModel> = PostItemDBModel::class.java

    override fun provideMapper(): IBaseDAOMapper = PostItemDBModel.Properties
}