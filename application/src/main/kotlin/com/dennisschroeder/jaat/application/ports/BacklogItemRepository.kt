package com.dennisschroeder.jaat.application.ports

import com.dennisschroeder.jaat.application.aggregates.backlogitem.BacklogItemId

interface BacklogItemRepository {
    fun getBacklogItemById(backlogItemId: BacklogItemId)
}
