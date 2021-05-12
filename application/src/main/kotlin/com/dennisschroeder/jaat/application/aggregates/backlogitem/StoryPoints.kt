package com.dennisschroeder.jaat.application.aggregates.backlogitem

import com.dennisschroeder.jaat.microarchitecture.Value

@JvmInline
value class StoryPoints private constructor(val value: Int) {
    companion object {
        private val allowedStoryPointValues = listOf(1, 2, 3, 5, 8, 13, 20)

        fun from(value: Int): Value<StoryPoints> {
            if (value > allowedStoryPointValues.last()) return Value.Invalid("The story is too big!")
            if (value !in allowedStoryPointValues) return Value.Invalid("Story points must be one of $allowedStoryPointValues")
            return Value.Valid(StoryPoints(value))
        }
    }
}
