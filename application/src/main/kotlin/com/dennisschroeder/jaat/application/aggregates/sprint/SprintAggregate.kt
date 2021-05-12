package com.dennisschroeder.jaat.application.aggregates.sprint

import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemId
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.application.aggregates.sprint.CommittedBacklogItemEntity

class SprintAggregate private constructor(private val sprintRootEntity: SprintRootEntity) {

    val sprintId
        get() = sprintRootEntity.sprintId

    val productId
        get() = sprintRootEntity.productId

    val sprintName
        get() = sprintRootEntity.sprintName

    val sprintBegin
        get() = sprintRootEntity.sprintBegin

    val sprintEnd
        get() = sprintRootEntity.sprintEnd

    val committedBacklogItemIds
        get() = sprintRootEntity
            .committedBacklogItems
            .map { it.backlogItemId }
            .toSet()

    companion object {
        fun build(
            sprintId: SprintId,
            productId: ProductId,
            sprintName: SprintName,
            sprintBegin: SprintBegin,
            sprintEnd: SprintEnd,
            committedBacklogItemIds: Set<BacklogItemId>
        ): Return<SprintAggregate> =
            Return.Success(
                SprintAggregate(
                    SprintRootEntity(
                        sprintId = sprintId,
                        productId = productId,
                        sprintName = sprintName,
                        sprintBegin = sprintBegin,
                        sprintEnd = sprintEnd,
                        committedBacklogItems = committedBacklogItemIds.map {
                            CommittedBacklogItemEntity(it)
                        }.toSet()
                    )
                )
            )
    }
}
