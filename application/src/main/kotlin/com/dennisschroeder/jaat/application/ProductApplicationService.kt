package com.dennisschroeder.jaat.application

import com.dennisschroeder.jaat.application.aggregates.product.ProductAggregate
import com.dennisschroeder.jaat.application.aggregates.product.ProductDescription
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.product.ProductName
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintBegin
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintEnd
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintName
import com.dennisschroeder.jaat.application.ports.ProductApplicationService
import com.dennisschroeder.jaat.application.ports.ProductRepository
import com.dennisschroeder.jaat.application.ports.SprintRepository
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.microarchitecture.Return.Failure
import com.dennisschroeder.jaat.microarchitecture.Return.Success
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr
import com.dennisschroeder.jaat.microarchitecture.returnIfSuccessOr

class ProductApplicationServiceImpl(
    private val productRepository: ProductRepository,
    private val sprintRepository: SprintRepository
) : ProductApplicationService {

    override fun addFreshProduct(name: ProductName, description: ProductDescription): Return<ProductId> {
        val validProductId = ProductId.fromRandomUUID().getIfValidOr { return@addFreshProduct Failure(it) }
        val freshProduct: ProductAggregate = ProductAggregate.build(validProductId, description, name)
            .returnIfSuccessOr { return@addFreshProduct Failure(it) }
        return productRepository.add(freshProduct)
    }

    override fun showSingleProduct(productId: ProductId): Return<ProductAggregate>? =
        productRepository.productOfId(productId)

    override fun scheduleSprint(productId: ProductId, name: SprintName, begin: SprintBegin, end: SprintEnd): Return<String> {
        val validProductAggregate =
            productRepository
                .productOfId(productId)
                ?.let { it.returnIfSuccessOr { reason ->  return@scheduleSprint Failure(reason) } }
                ?: return Failure("Product with id: ${productId.value} not found.")

        return when(val sprintAggregateScheduling = validProductAggregate.scheduleSprint(name, begin, end)) {
            is Failure -> Failure(sprintAggregateScheduling.reason)
            is Success -> runCatching {
                sprintRepository.add(sprintAggregateScheduling.result)
                Success("Successfully added sprint of Id: ${sprintAggregateScheduling.result.sprintId}")
            }.getOrElse { Failure(it.message ?: "Scheduling sprint with id: ${sprintAggregateScheduling.result.sprintId} failed") }
        }
    }

    override fun planProductBacklogItem(): Return<String> {
        TODO("Not yet implemented")
    }
}
