package chistousov.ilya.common_impl

import android.content.Context
import chistousov.ilya.common.Resource
import javax.inject.Inject

class AndroidResource (
    private val applicationContext: Context
) : Resource {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }
}