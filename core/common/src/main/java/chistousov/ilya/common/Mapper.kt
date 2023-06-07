package chistousov.ilya.common

interface Mapper <T, R> {

    fun map(input: T): R

    fun reverseMap(output: R): T
}

interface ListMapper<T, R> : Mapper<T, R> {
    fun mapList(input: List<T>): List<R>
}
