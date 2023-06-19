package tech.borgranch.equalkart.domain.model

data class ShoppingCartItem(
    val product: Product,
    val quantity: Int,
) {
    fun editQuantity(quantity: Int): ShoppingCartItem {
        return ShoppingCartItem(product, quantity)
    }
}
