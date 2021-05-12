package com.dennisschroeder.jaat.driveradapter.product

import java.time.OffsetDateTime

data class ScheduleSprintCommandBody(val name: String, val begin: OffsetDateTime, val end: OffsetDateTime)
