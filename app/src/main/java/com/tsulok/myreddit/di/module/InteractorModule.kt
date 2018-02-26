package com.tsulok.myreddit.di.module

import com.tsulok.myreddit.domain.interactor.posts.IPostsInteractor
import com.tsulok.myreddit.domain.interactor.posts.PostsInteractor
import dagger.Binds
import dagger.Module

/**
 * DI module wrapper for Interactors
 */
@Module(includes = [

])
abstract class InteractorModule {

    @Binds
    abstract fun providePostInteractor(interactor: PostsInteractor): IPostsInteractor
}