package chistousov.ilya.common

interface Mapper <T, R> {

    fun map(input: T): R

    fun reverseMap(output: R): T
}
