package com.dennisschroeder.jaat.drivenadapter.product

import com.dennisschroeder.jaat.application.aggregates.product.ProductAggregate
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.ports.ProductRepository
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.microarchitecture.Return.Failure
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr

class ProductJPARepository(private val productDAO: ProductDAO) : ProductRepository {
    override fun productOfId(productId: ProductId): Return<ProductAggregate>? {
        val productDTOOptional = productDAO.findById(productId.toUUID())
        if (productDTOOptional.isEmpty) return null

        return productDTOOptional.get().toProductAggregate()
    }

    override fun add(productAggregate: ProductAggregate): Return<ProductId> =
        runCatching {
            val newProduct = productDAO.saveAndFlush(ProductDTO.fromProductAggregate(productAggregate))
            val productId = ProductId.from(newProduct.id.toString()).getIfValidOr { return@add Failure(it) }
            Return.Success(productId)
        }.getOrElse { Failure(it.message ?: "Could not add a fresh product named: ${productAggregate.productName.value}") }

}
