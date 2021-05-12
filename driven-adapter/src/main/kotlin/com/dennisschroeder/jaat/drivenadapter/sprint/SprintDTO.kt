package com.dennisschroeder.jaat.drivenadapter.sprint

import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemId
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintAggregate
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintBegin
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintEnd
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintId
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintName
import com.dennisschroeder.jaat.microarchitecture.Return.Failure
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr
import org.hibernate.annotations.Type
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "SPRINT")
data class SprintDTO(
    @Id
    @Type(type = "uuid-char") @Column(length = 36)
    val id: UUID = UUID.randomUUID(),
    val productId: UUID,
    val sprintName: String,
    val sprintBegin: OffsetDateTime,
    val sprintEnd: OffsetDateTime,
    @Embedded
    val committedBacklogItems: Set<CommittedBacklogItemDTO>
) {
    companion object {
        fun fromSprintAggregate(sprintAggregate: SprintAggregate) =
            SprintDTO(
                id = sprintAggregate.sprintId.toUUID(),
                productId = sprintAggregate.productId.toUUID(),
                sprintName = sprintAggregate.sprintName.value,
                sprintBegin = sprintAggregate.sprintBegin.value,
                sprintEnd = sprintAggregate.sprintEnd.value,
                committedBacklogItems = sprintAggregate
                    .committedBacklogItemIds
                    .map { CommittedBacklogItemDTO(it.toUUID()) }
                    .toSet()
            )
    }
}

@Embeddable
data class CommittedBacklogItemDTO(val backlogItemId: UUID)

fun SprintDTO.toSprintAggregate() =
    SprintAggregate.build(
        sprintId = SprintId.from(id.toString()).getIfValidOr { return@toSprintAggregate Failure(it) },
        productId = ProductId.from(productId.toString()).getIfValidOr { return@toSprintAggregate Failure(it) },
        sprintName = SprintName(sprintName),
        sprintBegin = SprintBegin(sprintBegin),
        sprintEnd = SprintEnd(sprintEnd),
        committedBacklogItemIds = committedBacklogItems.map { item ->
            BacklogItemId
                .from(item.backlogItemId.toString())
                .getIfValidOr { return@toSprintAggregate Failure(it) }
        }.toSet()
    )
