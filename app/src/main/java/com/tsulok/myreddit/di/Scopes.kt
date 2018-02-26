package com.tsulok.myreddit.di

import javax.inject.Scope

/**
 * Scopes for various DI injections
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

/**
 * Scope for fragments
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope
