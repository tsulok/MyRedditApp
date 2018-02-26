package com.tsulok.myreddit.utility.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tsulok.myreddit.di.ActivityContext
import javax.inject.Inject


/**
 * Helper for UI components
 */
class ToastHelper
@Inject constructor(@ActivityContext val context: Context) : IToastHelper {
    var toast: Toast? = null

    override fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }

    override fun makeToastOnUiThread(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post({ this.makeToast(message) })
    }

    override fun makeToast(messageId: Int) {
        makeToast(context.getString(messageId))
    }
}

interface IToastHelper {
    /**
     * Make toast with the given message
     * @param message The message to be shown
     */
    fun makeToast(message: String)

    /**
     * Make toast with the given message forced on the main thread
     * @param message The the message to be shown
     */
    fun makeToastOnUiThread(message: String)

    /**
     * Make toast with the given message
     * @param messageId The id of the message to be shown
     */
    fun makeToast(messageId: Int)
}