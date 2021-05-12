package com.dennisschroeder.jaat.drivenadapter.product

import com.dennisschroeder.jaat.application.aggregates.product.ProductAggregate
import com.dennisschroeder.jaat.application.aggregates.product.ProductDescription
import com.dennisschroeder.jaat.application.aggregates.product.ProductId
import com.dennisschroeder.jaat.application.aggregates.product.ProductName
import com.dennisschroeder.jaat.microarchitecture.Return
import com.dennisschroeder.jaat.microarchitecture.Return.*
import com.dennisschroeder.jaat.microarchitecture.getIfValidOr
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "PRODUCT")
data class ProductDTO(
    @Id
    @Type(type = "uuid-char") @Column(length = 36)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String
) {
    companion object {
        fun fromProductAggregate(productAggregate: ProductAggregate) =
            ProductDTO(
                id = productAggregate.productId.toUUID(),
                name = productAggregate.productName.value,
                description = productAggregate.productDescription.value
            )
    }
}

fun ProductDTO.toProductAggregate(): Return<ProductAggregate> =
    ProductAggregate.build(
        productId = ProductId.from(id.toString()).getIfValidOr { return@toProductAggregate Failure(it) },
        description = ProductDescription(description),
        name = ProductName(name)
    )
