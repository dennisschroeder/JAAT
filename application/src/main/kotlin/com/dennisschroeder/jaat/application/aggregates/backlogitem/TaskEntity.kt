package com.dennisschroeder.jaat.application.aggregates.backlogitem

internal data class TaskEntity(
    val taskId: TaskId,
    val title: String,
    val description: String,
    val hoursRemaining: Int
)
