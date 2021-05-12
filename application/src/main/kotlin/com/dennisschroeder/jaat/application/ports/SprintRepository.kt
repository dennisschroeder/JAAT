package com.dennisschroeder.jaat.application.ports

import com.dennisschroeder.jaat.application.aggregates.sprint.SprintAggregate
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintId
import com.dennisschroeder.jaat.microarchitecture.Return

interface SprintRepository {
    fun sprintOfId(sprintId: SprintId): Return<SprintAggregate>?
    fun add(sprintAggregate: SprintAggregate): Return<SprintId>
}
