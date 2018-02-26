package com.tsulok.myreddit.ui.stories.mainpage

import com.tsulok.myreddit.architecture.BasePresenter
import javax.inject.Inject

/**
 * Presenter for Main
 */

class MainPagePresenter
@Inject constructor()
    : BasePresenter<MainPageContract.View>(), MainPageContract.Presenter {

}