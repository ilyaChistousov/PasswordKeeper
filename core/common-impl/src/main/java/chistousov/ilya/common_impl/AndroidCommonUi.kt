package chistousov.ilya.common_impl

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import chistousov.ilya.common.AlertDialogConfig
import chistousov.ilya.common.CommonUi

class AndroidCommonUi(private val appContext: Context) : CommonUi, ActivityRequired {

    private var currentActivity: FragmentActivity? = null

    override fun showToast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun showAlertDialog(config: AlertDialogConfig) {
        startDialog(config)
    }

    override fun onCreate(activity: FragmentActivity) {
        this.currentActivity = activity
    }

    private fun startDialog(config: AlertDialogConfig) {
        val builder = AlertDialog.Builder(currentActivity)
            .setTitle(config.title)
            .setMessage(config.message)
            .setPositiveButton(config.positiveButtonText) { _, _ ->
                config.positiveButtonCallback()
            }
        if (config.negativeButtonText != null) {
            builder.setNegativeButton(config.negativeButtonText) { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}