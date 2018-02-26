package com.tsulok.myreddit.utility.helper

import android.content.Context
import android.support.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.tsulok.myreddit.R
import com.tsulok.myreddit.di.ActivityContext
import javax.inject.Inject


/**
 * Common alerting helper
 */

class AlertHelper
@Inject constructor(@ActivityContext val context: Context) : IAlertHelper {

    private var defaultMessage: Int = R.string.loading_default
    val progressDialog: MaterialDialog

    init {
        progressDialog = createDefaultLoadingMaterialDialog(R.string.loading_default)
    }

    /**
     * Creates a defailt loading progress dialog
     * @param messageId The resource id of the message
     * *
     * @return A progress dialog
     */
    private fun createDefaultLoadingMaterialDialog(@StringRes messageId: Int): MaterialDialog {
        defaultMessage = messageId
        return MaterialDialog.Builder(context)
                .content(messageId)
                .progress(true, 0)
                .cancelable(false)
                .build()
    }

    override fun showProgressDialog(message: String?, isCancellable: Boolean) {
        progressDialog.setContent(message ?: context.getString(defaultMessage))
        progressDialog.setCancelable(isCancellable)
        progressDialog.setCanceledOnTouchOutside(isCancellable)

        progressDialog.show()
    }

    override fun showProgressDialog(@StringRes messageId: Int,
                                    isCancellable: Boolean) {
        showProgressDialog(context.getString(messageId), isCancellable)
    }

    override fun hideProgressDialog() {
        progressDialog.dismiss()
    }
}

/**
 * Open actions on alert helper
 */
interface IAlertHelper {
    /**
     * Shows a progress dialog
     * @param messageId Show the message with the given id
     *                  If non present the default message will be shown
     */
    fun showProgressDialog(@StringRes messageId: Int,
                           isCancellable: Boolean = false)

    /**
     * Shows a progress dialog
     * @param messageId Show the message with the given id
     *                  If non present the default message will be shown
     */
    fun showProgressDialog(message: String? = null,
                           isCancellable: Boolean = false)

    /**
     * Hide the shown progress dialog
     */
    fun hideProgressDialog()
}