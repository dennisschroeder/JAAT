package com.dennisschroeder.jaat.application.aggregates.backlogitem

import com.dennisschroeder.jaat.microarchitecture.Value
import java.util.UUID

@JvmInline
value  class TaskId private constructor(val value: String) {
    companion object {
        fun from(value: String): Value<TaskId> {
            if (value.isEmpty()) return Value.Invalid("The string value shall not be empty")
            return validateUUIDValue(value).getOrElse {
                Value.Invalid(it.message ?: "Value is not a valid UUID")
            }
        }

        fun fromRandomUUID(): Value<TaskId> =
            from(UUID.randomUUID().toString())

        private fun validateUUIDValue(value: String) =
            runCatching {
                UUID.fromString(value)
                Value.Valid(TaskId(value))
            }
    }
}
