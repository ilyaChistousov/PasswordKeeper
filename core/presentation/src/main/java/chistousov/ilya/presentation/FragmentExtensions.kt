package chistousov.ilya.presentation

import androidx.fragment.app.Fragment


const val SCREEN_ARGS = "screen args"

fun <T : BaseScreenArgs> Fragment.args() : T {
    return requireArguments().getSerializable(SCREEN_ARGS) as? T
        ?: throw IllegalStateException("args is null")
}