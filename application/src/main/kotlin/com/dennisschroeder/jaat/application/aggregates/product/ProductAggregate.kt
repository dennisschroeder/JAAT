package com.dennisschroeder.jaat.application.aggregates.product

import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemAggregate
import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemId
import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemName
import com.dennisschroeder.jaat.application.aggregates.backlogitem.StoryPoints
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintAggregate
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintBegin
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintEnd
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintId
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintName
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr

class ProductAggregate private constructor(
    private val productRootEntity: ProductRootEntity
) {
    val productId
        get() = productRootEntity.id

    val productName
        get() = productRootEntity.name

    val productDescription
        get() = productRootEntity.description

    fun planProductBacklogItem(storyPoints: StoryPoints, name: BacklogItemName): Return<BacklogItemAggregate> =
        BacklogItemAggregate.build(
            productId = productRootEntity.id,
            storyPoints = storyPoints,
            name = name,
            backlogItemId = BacklogItemId.fromRandomUUID()
                .getIfValidOr { return@planProductBacklogItem Return.Failure(it) },
        )

    fun scheduleSprint(name: SprintName, begin: SprintBegin, end: SprintEnd): Return<SprintAggregate> =
        SprintAggregate.build(
            productId = productRootEntity.id,
            sprintName = name,
            sprintBegin = begin,
            sprintEnd = end,
            committedBacklogItemIds = emptySet(),
            sprintId = SprintId.fromRandomUUID()
                .getIfValidOr { return@scheduleSprint Return.Failure(it) },
        )

    companion object {
        fun build(productId: ProductId, description: ProductDescription, name: ProductName): Return<ProductAggregate> =
            Return.Success(
                ProductAggregate(
                    productRootEntity = ProductRootEntity(
                        id = productId,
                        description = description,
                        name = name,
                    )
                )
            )
    }
}
