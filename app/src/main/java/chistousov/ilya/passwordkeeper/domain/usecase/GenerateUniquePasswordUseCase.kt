package chistousov.ilya.passwordkeeper.domain.usecase

import javax.inject.Inject

class GenerateUniquePasswordUseCase @Inject constructor() {

    private val digitsList = ('0'..'9').toList()
    private val uppercaseList = ('A'..'Z').toList()
    private val specialList = listOf(
        '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=',
        '_', '|', '/', '?', '~', '<', '>', '.', '{', '}', '[', ']', '\\'
    )

    operator fun invoke(
        length: Int,
        withDigits: Boolean,
        withUppercase: Boolean,
        withSpecial: Boolean
    ): String {
        val lowercaseList = ('a'..'z').toMutableList()

        if (withDigits) {
            lowercaseList.addAll(digitsList)
        }
        if (withUppercase) {
            lowercaseList.addAll(uppercaseList)
        }
        if (withSpecial) {
            lowercaseList.addAll(specialList)
        }
        val password = StringBuilder()
        repeat(length) {
            val nextChar = lowercaseList.random()
            password.append(nextChar)
        }
        return password.toString()
    }
}
