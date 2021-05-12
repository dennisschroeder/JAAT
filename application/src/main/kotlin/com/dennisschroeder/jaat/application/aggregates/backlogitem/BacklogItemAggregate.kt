package com.dennisschroeder.jaat.application.aggregates.backlogitem

import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.microarchitecture.Return
import java.util.Collections.emptyList

class BacklogItemAggregate private constructor(private val backlogItemRootEntity: BackLogItemRootEntity) {

    companion object {
        fun build(
            backlogItemId: BacklogItemId,
            productId: ProductId,
            name: BacklogItemName,
            storyPoints: StoryPoints

        ): Return<BacklogItemAggregate> =
            Return.Success(
                BacklogItemAggregate(
                    BackLogItemRootEntity(
                        backlogItemId = backlogItemId,
                        name = name,
                        productId = productId,
                        tasks = emptyList(),
                        status = BacklogItemStatus.PLANNED,
                        storyPoints = storyPoints
                    )
                )
            )
    }
}
