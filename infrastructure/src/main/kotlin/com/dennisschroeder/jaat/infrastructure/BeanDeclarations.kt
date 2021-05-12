package com.dennisschroeder.jaat.infrastructure

import com.dennisschroeder.jaat.application.ProductApplicationServiceImpl
import com.dennisschroeder.jaat.application.ports.ProductApplicationService
import com.dennisschroeder.jaat.application.ports.ProductRepository
import com.dennisschroeder.jaat.application.ports.SprintRepository
import com.dennisschroeder.jaat.drivenadapter.product.ProductJPARepository
import com.dennisschroeder.jaat.drivenadapter.sprint.SprintJPARepository
import com.dennisschroeder.jaat.driveradapter.product.ProductCommandsHandler
import com.dennisschroeder.jaat.driveradapter.product.ProductRouter
import org.springframework.context.support.beans

fun beanDeclarations() = beans {

    bean<ProductRepository> { ProductJPARepository(ref()) }

    bean<SprintRepository> { SprintJPARepository(ref()) }

    bean<ProductApplicationService> { ProductApplicationServiceImpl(ref(), ref()) }

    bean { ProductCommandsHandler(ref()) }

    bean { ProductRouter(ref()) }
}
