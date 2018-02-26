package com.tsulok.myreddit.domain.repository.post

import dagger.Binds
import dagger.Module

/**
 * Dagger module for post repository
 */
@Module()
abstract class PostRepositoryModule {

    @Binds
    abstract fun providePostRepository(repository: PostRepository): IPostRepository
}