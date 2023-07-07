package tech.borgranch.equalkart.domain.model

import tech.borgranch.equalkart.R
import tech.borgranch.equalkart.data.local.dto.CachedProduct
import tech.borgranch.equalkart.utility.capitalizeWords

data class Product(
    val sku: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val inStock: Boolean = false,
    val imageResId: Int = R.drawable.product_image_placeholder,
) {

    companion object {
        fun empty(): Product {
            return Product("", "", "", 0.0, "", false)
        }

        fun fromCachedProduct(it: CachedProduct): Product {
            return Product(
                it.sku,
                it.name.capitalizeWords(),
                it.description,
                it.price,
                it.image,
                it.inStock,
            )
        }
    }

    fun editDescription(description: String): Product {
        require(description.isNotBlank()) { "Description must not be blank" }
        return Product(sku, name, description, price, image, inStock)
    }

    fun editName(name: String): Product {
        require(name.isNotBlank()) { "Name must not be blank" }
        return Product(sku, name.capitalizeWords(), description, price, image, inStock)
    }

    fun editPrice(price: Double): Product {
        require(price >= 0.0) { "Price must be non-negative" }
        return Product(sku, name, description, price, image, inStock)
    }
}
