package chistousov.ilya.common_impl

import android.util.Log
import chistousov.ilya.common.Logger
import javax.inject.Inject

class AndroidLogger : Logger {

    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }
}