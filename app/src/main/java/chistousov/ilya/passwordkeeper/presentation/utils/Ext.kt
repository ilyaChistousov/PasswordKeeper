package chistousov.ilya.passwordkeeper.presentation.utils

import androidx.fragment.app.Fragment


fun Fragment.getStringNullable(resId: Int?): String? {
    return resId?.let {
        getString(it)
    }
}