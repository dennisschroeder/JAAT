package com.dennisschroeder.jaat.application.aggregates.sprint

import com.dennisschroeder.jaat.application.aggregates.product.ProductId

internal data class SprintRootEntity(
    val sprintId: SprintId,
    val productId: ProductId,
    val sprintName: SprintName,
    val sprintBegin: SprintBegin,
    val sprintEnd: SprintEnd,
    val committedBacklogItems: Set<CommittedBacklogItemEntity>
)
