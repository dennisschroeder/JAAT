package com.dennisschroeder.jaat.application.aggregates.product

internal data class ProductRootEntity (
    val id: ProductId,
    val name: ProductName,
    val description: ProductDescription,
)
