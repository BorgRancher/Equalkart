package tech.borgranch.equalkart.domain.model

data class Product(
    val name: String,
    val price: Double,
) {
    companion object {
        fun empty(): Product {
            return Product("", 0.0)
        }
    }
    fun editName(name: String): Product {
        require(name.isNotBlank()) { "Name must not be blank" }
        return Product(name, price)
    }
    fun editPrice(price: Double): Product {
        require(price >= 0.0) { "Price must be non-negative" }
        return Product(name, price)
    }
}
