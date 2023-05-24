package chistousov.ilya.passwordkeeper.utils

import chistousov.ilya.passwordkeeper.R

class Validator {

    private val rules = mutableListOf(REQUIRED_FIELD)

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

    class Rule(private val rule: Regex, private val message: Int) {

        fun isValid(value: String): Pair<Boolean, Int> {
            return rule.matches(value) to message
        }
    }

    companion object {
        val REQUIRED_FIELD = Rule(Regex("^.+\$"), R.string.required_field)
        const val PASSWORD_KEY = "password"
        const val TITLE_KEY = "title"
    }
}