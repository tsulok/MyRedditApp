package com.tsulok.myreddit.di.module

import com.tsulok.myreddit.domain.repository.database.RealmInstanceProviderModule
import com.tsulok.myreddit.domain.repository.post.PostRepositoryModule
import dagger.Module

/**
 * DI module wrapper for Repositories
 */
@Module(includes = [
    PostRepositoryModule::class,
    RealmInstanceProviderModule::class]
)
abstract class RepositoryModule