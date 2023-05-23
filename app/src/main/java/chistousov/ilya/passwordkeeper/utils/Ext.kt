package chistousov.ilya.passwordkeeper.utils

import androidx.fragment.app.Fragment


fun Fragment.getStringNullable(resId: Int?): String? {
    return resources.getString(resId ?: return null)
}