package com.tsulok.myreddit.ui.stories.mainpage

import com.tsulok.myreddit.architecture.IBasePresenter
import com.tsulok.myreddit.architecture.IBaseView

/**
 * This specifies the contract between the view and the presenter.
 */

interface MainPageContract {

    /**
     * Updates on the UI from the presenter
     */
    interface View : IBaseView {

    }

    /**
     * Actions on the presenter
     */
    interface Presenter : IBasePresenter<View> {

    }
}