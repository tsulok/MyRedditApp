package com.tsulok.myreddit.di.module

import com.tsulok.myreddit.utility.helper.AlertHelper
import com.tsulok.myreddit.utility.helper.IAlertHelper
import com.tsulok.myreddit.utility.helper.IToastHelper
import com.tsulok.myreddit.utility.helper.ToastHelper
import dagger.Binds
import dagger.Module

/**
 * Binding for ui helper
 */
@Module
abstract class UIHelperModule {

    @Binds
    abstract fun provideAlertHelper(alertHelper: AlertHelper): IAlertHelper

    @Binds
    abstract fun provideToastHelper(toastHelper: ToastHelper): IToastHelper
}