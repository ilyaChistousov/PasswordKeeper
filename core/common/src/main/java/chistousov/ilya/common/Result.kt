package chistousov.ilya.common

sealed class Result<out T> {

    abstract fun unwrap() : T
    abstract fun <R> map (mapper : (T) -> R) : Result<R>

    data class Success<T>(val value: T) : Result<T>() {
        override fun unwrap() = value
        override fun <R> map(mapper: (T) -> R): Result<R> {
            return Success(mapper(value))
        }
    }

    data class Error (val message: String): Result<Nothing>() {
        override fun unwrap(): Nothing {
            throw Exception(message)
        }

        override fun <R> map(mapper: (Nothing) -> R): Result<R> {
            return this
        }
    }
}