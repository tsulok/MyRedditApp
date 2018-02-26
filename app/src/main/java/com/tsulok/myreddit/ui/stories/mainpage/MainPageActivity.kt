package com.tsulok.myreddit.ui.stories.mainpage

import android.app.Fragment
import com.tsulok.myreddit.R
import com.tsulok.myreddit.extensions.replaceFragment
import com.tsulok.myreddit.ui.base.BaseActivity
import com.tsulok.myreddit.ui.stories.mainpage.posts.MainPagePostsListFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

class MainPageActivity : BaseActivity<MainPageContract.View, MainPageContract.Presenter>(),
        MainPageContract.View, HasFragmentInjector {

    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun loadContentId(): Int = R.layout.activity_main

    override fun initObjects() {
        super.initObjects()
        replaceFragment(R.id.frameRoot, MainPagePostsListFragment())
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector
}
