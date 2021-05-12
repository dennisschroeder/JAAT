package com.dennisschroeder.jaat.application.ports

import com.dennisschroeder.jaat.application.aggregates.product.ProductAggregate
import com.dennisschroeder.jaat.application.aggregates.product.ProductDescription
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.product.ProductName
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintBegin
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintEnd
import com.dennisschroeder.jaat.application.aggregates.sprint.SprintName
import com.dennisschroeder.jaat.microarchitecture.Return

interface ProductApplicationService {
    fun addFreshProduct(name: ProductName, description: ProductDescription): Return<ProductId>
    fun showSingleProduct(productId: ProductId): Return<ProductAggregate>?
    fun scheduleSprint(productId: ProductId, name: SprintName, begin: SprintBegin, end: SprintEnd): Return<String>
    fun planProductBacklogItem(): Return<String>
}
