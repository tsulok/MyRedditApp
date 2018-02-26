package com.tsulok.myreddit.di

import javax.inject.Qualifier

/**
 * Separate qualifiers
 */


/**
 * Network scoped qualifier
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Network

/**
 * Activity scoped context
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

/**
 * Application scoped context
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext


/**
 * FragmentManager qualifier
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildFragmentManager


/**
 * FragmentManager qualifier for support
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupportFragmentManager