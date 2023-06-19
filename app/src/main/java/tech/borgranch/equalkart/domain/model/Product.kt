package tech.borgranch.equalkart.domain.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val image: String,
) {
    companion object {
        fun empty(): Product {
            return Product(0, "", 0.0, "", "")
        }
    }
    fun editName(name: String): Product {
        return Product(id, name, price, description, image)
    }
    fun editPrice(price: Double): Product {
        return Product(id, name, price, description, image)
    }
    fun editDescription(description: String): Product {
        return Product(id, name, price, description, image)
    }
    fun editImage(image: String): Product {
        return Product(id, name, price, description, image)
    }
}
