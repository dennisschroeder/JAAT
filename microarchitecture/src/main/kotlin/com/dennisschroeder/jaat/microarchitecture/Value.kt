package com.dennisschroeder.jaat.microarchitecture

sealed class Value<out T> {
    data class Valid<out R>(val value: R) : Value<R>()
    data class Invalid(val reason: String) : Value<Nothing>()
}

inline fun<reified T> Value<T>.getIfValidOr(callback: (reason: String) -> T): T =
    when(this) {
        is Value.Valid -> value
        is Value.Invalid -> callback(reason)
    }

inline fun<reified T, reified R> Value<T>.mapIfValid(mapper: (T) -> R): Value<R> =
    when(this) {
        is Value.Valid -> Value.Valid(mapper(value))
        is Value.Invalid -> this
    }
