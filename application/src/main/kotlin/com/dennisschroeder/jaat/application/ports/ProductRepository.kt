package com.dennisschroeder.jaat.application.ports

import com.dennisschroeder.jaat.application.aggregates.product.ProductAggregate
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.microarchitecture.Return

interface ProductRepository {
    fun productOfId(productId: ProductId): Return<ProductAggregate>?
    fun add(productAggregate: ProductAggregate): Return<ProductId>
}
