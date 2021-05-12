package com.dennisschroeder.jaat.application.aggregates.backlogitem

import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintId

internal data class BackLogItemRootEntity(
    val backlogItemId: BacklogItemId,
    val name: BacklogItemName,
    val productId: ProductId,
    val status: BacklogItemStatus,
    val tasks: List<TaskEntity>,
    val storyPoints: StoryPoints
)
