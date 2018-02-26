package com.tsulok.myreddit.domain.network.utility.parser

import dagger.Binds
import dagger.Module

/**
 * Dagger module for networking errors
 */
@Module()
abstract class NetworkErrorDIModule {

    @Binds
    abstract fun provideNetworkErrorChecker(networkErrorCheckerParser: NetworkErrorCheckerParser): INetworkErrorCheckerParser
}