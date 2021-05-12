package com.dennisschroeder.jaat.application.aggregates.sprint

import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemId

internal data class CommittedBacklogItemEntity(
    val backlogItemId: BacklogItemId
)
