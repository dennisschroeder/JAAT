package com.dennisschroeder.jaat.driveradapter.product

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

@Suppress("FunctionName")
fun ProductRouter(productCommandsHandler: ProductCommandsHandler) = router {
    accept(MediaType.APPLICATION_JSON).nest {
        "product/".nest {
            "commands/".nest {
                POST("addFreshProduct", productCommandsHandler::handleAddFreshProductCommand)
                GET("showSingleProduct/{productId}", productCommandsHandler::handleShowSingleProductCommand)
                POST("scheduleSprint/{productId}", productCommandsHandler::handleScheduleSprintCommand)
            }
        }
    }
}
