package com.dennisschroeder.jaat.drivenadapter.sprint

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SprintDAO : JpaRepository<SprintDTO, UUID>
