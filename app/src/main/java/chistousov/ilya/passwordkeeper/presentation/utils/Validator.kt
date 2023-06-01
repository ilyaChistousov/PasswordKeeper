package chistousov.ilya.passwordkeeper.presentation.utils

import chistousov.ilya.passwordkeeper.R
import java.util.concurrent.locks.Condition

class Validator {

    private val rules = mutableListOf(requiredField())

    fun validate(value: String): Int? {
        for (rule in rules) {
            val (isValid, message) = rule.isValid(value)
            if (!isValid) {
                return message
            }
        }
        return null
    }

    fun addRule(rule: Rule) {
        rules.add(rule)
    }

    class Rule(private val pattern: Regex, private val resId: Int) {
        fun isValid(value: String): Pair<Boolean, Int> {
            return pattern.matches(value) to resId
        }
    }

    companion object {
        fun requiredField(): Rule = Rule(Regex("^.+\$"), R.string.required_field)

        fun maximumLength(length: Int, resId: Int): Rule = Rule(
            Regex("^.{1,$length}$"),
            resId
        )

        fun minimumLength(length: Int, resId: Int): Rule = Rule(
            Regex("^.{$length,}$"),
            resId
        )

        fun sameText(text: String, resId: Int): Rule = Rule(
            Regex(text),
            resId
        )

        const val PASSWORD_KEY = "password"
        const val CONFIRM_PASSWORD_KEY = "confirmPassword"
        const val TITLE_KEY = "title"
    }
}