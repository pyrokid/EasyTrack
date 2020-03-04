package com.easytrack.app.utils.ui

import android.content.Context

object ThrowableHandler {

    fun getCauseMessage(throwable: Throwable, context: Context): String {
        return "Something went wrong"
    }
}