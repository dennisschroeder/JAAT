package com.dennisschroeder.jaat.driveradapter.product

import com.dennisschroeder.jaat.application.aggregates.product.ProductDescription
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.product.ProductName
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintBegin
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintEnd
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintName
import com.dennisschroeder.jaat.application.ports.ProductApplicationService
import com.dennisschroeder.jaat.microarchitecture.Return.Failure
import com.dennisschroeder.jaat.microarchitecture.Return.Success
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class ProductCommandsHandler(private val applicationService: ProductApplicationService) {

    fun handleAddFreshProductCommand(request: ServerRequest): ServerResponse {
        val commandBody = request.body(AddFreshProductCommandBody::class.java)
        val commandResult = applicationService.addFreshProduct(
            ProductName(commandBody.name),
            ProductDescription(commandBody.description)
        )

        return when (commandResult) {
            is Failure -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(commandResult.reason)
            is Success -> ServerResponse.ok().body(commandResult.result.value)
        }
    }

    fun handleShowSingleProductCommand(request: ServerRequest): ServerResponse {
        val productIdFromPath = request.pathVariable("productId")
        val validProductId = ProductId.from(productIdFromPath).getIfValidOr {
            return@handleShowSingleProductCommand ServerResponse.status(HttpStatus.BAD_REQUEST).body(it)
        }

        val productAggregate = applicationService.showSingleProduct(validProductId)

        return productAggregate?.let {
            when (it) {
                is Success -> ServerResponse.ok().body(
                    ProductResponse(
                        id = it.result.productId.value,
                        description = it.result.productDescription.value,
                        name = it.result.productName.value
                    )
                )
                is Failure -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(it.reason)
            }
        } ?: ServerResponse.status(HttpStatus.NOT_FOUND)
            .body("Product of id: ${validProductId.value} could not be found")
    }

    fun handleScheduleSprintCommand(request: ServerRequest): ServerResponse {
        val productIdFromPath = request.pathVariable("productId")
        val commandBody = request.body(ScheduleSprintCommandBody::class.java)

        val validProductId = ProductId.from(productIdFromPath).getIfValidOr {
            return@handleScheduleSprintCommand ServerResponse.status(HttpStatus.BAD_REQUEST).body(it)
        }
        val commandResult = applicationService.scheduleSprint(
            productId = validProductId,
            name = SprintName(commandBody.name),
            begin = SprintBegin(commandBody.begin),
            end = SprintEnd(commandBody.end)
        )

        return when(commandResult) {
            is Failure -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commandResult.reason)
            is Success -> ServerResponse.ok().body(commandResult.result)
        }
    }
}
