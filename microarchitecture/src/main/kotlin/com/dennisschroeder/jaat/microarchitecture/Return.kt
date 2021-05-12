package com.dennisschroeder.jaat.microarchitecture

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Return<out T> {
    data class Success<out R>(val result: R) : Return<R>()
    data class Failure(val reason: String) : Return<Nothing>()
}

@OptIn(ExperimentalContracts::class)
inline fun<reified R, reified T : R> Return<T>.returnIfSuccessOr(onFailure: (reason: String) -> R): R {
    contract {
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }

    return when(this) {
        is Return.Success -> result
        is Return.Failure -> onFailure(reason)
    }
}

