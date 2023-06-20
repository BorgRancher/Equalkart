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
        return Product(name, price)
    }
    fun editPrice(price: Double): Product {
        return Product(name, price)
    }
}
