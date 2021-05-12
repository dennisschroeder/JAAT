package com.dennisschroeder.jaat.drivenadapter.sprint

import com.dennisschroeder.jaat.application.aggregates.sprint.SprintAggregate
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintId
import com.dennisschroeder.jaat.application.ports.SprintRepository
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.microarchitecture.Return.Failure
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr

class SprintJPARepository(private val sprintDAO: SprintDAO): SprintRepository {
    override fun sprintOfId(sprintId: SprintId): Return<SprintAggregate>? {
        val sprintDTOOptional = sprintDAO.findById(sprintId.toUUID())
        if (sprintDTOOptional.isEmpty) return null

        return sprintDTOOptional.get().toSprintAggregate()
    }

    override fun add(sprintAggregate: SprintAggregate): Return<SprintId> =
        runCatching {
            val newSprint = sprintDAO.saveAndFlush(SprintDTO.fromSprintAggregate(sprintAggregate))
            val sprintId = SprintId.from(newSprint.id.toString()).getIfValidOr { return@add Failure(it) }
            Return.Success(sprintId)
        }.getOrElse { Failure(it.message ?: "Could not add a new sprint named: ${sprintAggregate.sprintName}") }

}
