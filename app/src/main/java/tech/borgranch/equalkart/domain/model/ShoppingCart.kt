package tech.borgranch.equalkart.domain.model

import java.util.Locale

/**
 * A shopping cart is a collection of items, each with a product and a quantity.
 */
data class ShoppingCart(
    private val items: List<ShoppingCartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val isEmpty: Boolean = items.isEmpty(),
    val taxPercentage: Double = 0.21,
) {
    /**
     * Add an item to the cart.
     * If the item does not exist and the quantity is positive, it is added.
     * If the item already exists, the quantity is updated.
     * If the quantity is negative, the item is removed.
     */
    fun addItem(product: Product, quantity: Int = 1): ShoppingCart {
        // for an empty cart, only add items if quantity is positive
        if (this.isEmpty && quantity > 0) {
            return ShoppingCart(listOf(ShoppingCartItem(product, quantity)), subtotal, tax, total).calculate()
        }

        // for a non-empty cart, add items if quantity is positive, remove if negative
        val newItems = items.toMutableList()
        val item = newItems.find { it.product == product }
        when {
            item != null -> {
                newItems.remove(item)
                if (item.quantity + quantity > 0) {
                    newItems.add(item.editQuantity(item.quantity + quantity))
                }
            }
            quantity > 0 -> {
                newItems.add(ShoppingCartItem(product, quantity))
            }
        }
        // return a new cart with the updated items
        return ShoppingCart(newItems, subtotal, tax, total).calculate()
    }

    /**
     * Remove an item from the cart.
     * If the item does not exist, nothing happens.
     * If the item exists and the quantity is positive, the quantity is updated.
     */
    fun removeItem(product: Product, quantity: Int = Int.MIN_VALUE): ShoppingCart {
        if (quantity == Int.MIN_VALUE) {
            return(addItem(product, quantity))
        }
        return(addItem(product, quantity * -1))
    }

    fun empty(): ShoppingCart {
        return ShoppingCart(emptyList(), 0.0, 0.0, 0.0)
    }
    private fun calculateTax(): ShoppingCart {
        return ShoppingCart(items, subtotal, currencyFormat(subtotal * taxPercentage), total)
    }

    private fun calculateSubtotal(): ShoppingCart {
        return ShoppingCart(items, currencyFormat(items.sumOf { it.product.price * it.quantity }), tax, total)
    }

    private fun calculateTotal(): ShoppingCart {
        return ShoppingCart(items, subtotal, tax, currencyFormat(subtotal + tax))
    }
    private fun calculate(): ShoppingCart {
        return calculateSubtotal().calculateTax().calculateTotal()
    }

    fun checkout(): ShoppingCart {
        return empty()
    }

    fun getItemCount(product: Product? = null): Int {
        if (product == null) {
            return items.sumOf { it.quantity }
        }
        return items.find { it.product == product }?.quantity ?: 0
    }

    private fun currencyFormat(value: Double): Double {
        return String.format(
            Locale.getDefault(),
            "%.2f",
            value,
        ).replace(",", ".").toDouble()
    }
}
