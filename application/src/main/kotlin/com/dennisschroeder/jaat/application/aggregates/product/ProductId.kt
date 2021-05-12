package com.dennisschroeder.jaat.application.aggregates.product

import com.dennisschroeder.jaat.microarchitecture.Value
import java.util.UUID

class ProductId private constructor(val value: String) {
    companion object {
        fun from(value: String): Value<ProductId> {
            if (value.isEmpty()) return Value.Invalid("The string value shall not be empty")
            return validateUUIDValue(value).getOrElse {
                Value.Invalid(it.message ?: "Value is not a valid UUID")
            }
        }

        fun fromRandomUUID(): Value<ProductId> =
            from(UUID.randomUUID().toString())

        private fun validateUUIDValue(value: String) =
            runCatching {
                UUID.fromString(value)
                Value.Valid(ProductId(value))
            }
    }

    fun toUUID(): UUID = UUID.fromString(value)
}
