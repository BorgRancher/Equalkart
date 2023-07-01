package tech.borgranch.equalkart.domain.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.ConcurrentHashMap

/**
 * A shopping cart is a collection of items, each with a product and a quantity.
 */
data class ShoppingCart(
    private val items: ConcurrentHashMap<String, ShoppingCartItem> = ConcurrentHashMap(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val isEmpty: Boolean = items.isEmpty(),
    val taxPercentage: Double = 0.125,
) {
    /**
     * Add an item to the cart.
     * If the item does not exist and the quantity is positive, it is added.
     * If the item already exists, the quantity is updated.
     * If the quantity is negative, the item is removed.
     * Synchronized to prevent multiple threads from accessing the cart at the same time.
     */
    @Synchronized
    fun addItem(product: Product, quantity: Int = 1): ShoppingCart {
        // for an empty cart, only add items if quantity is positive
        if (this.isEmpty && quantity > 0) {
            items[product.name] = ShoppingCartItem(product, quantity)
            return ShoppingCart(
                items = items,
                subtotal = subtotal,
                tax = tax,
                total = total,
            ).calculate()
        }

        // for a non-empty cart, add items if quantity is positive, remove if negative
        val item = items.entries.firstOrNull { product.name == it.key }
        when {
            item != null -> {
                if ((item.value.quantity + quantity) >= 1) {
                    items[product.name] = item.value.editQuantity(item.value.quantity + quantity)
                } else {
                    items.remove(product.name)
                }
            }
            quantity > 0 -> {
                items[product.name] = ShoppingCartItem(product, quantity)
            }
        }
        // return a new cart with the updated items
        return ShoppingCart(items, subtotal, tax, total).calculate()
    }

    /**
     * Remove an item from the cart.
     * If the item does not exist, nothing happens.
     * If the item exists and the quantity is positive, the quantity is updated.
     */
    @Synchronized
    fun removeItem(product: Product, quantity: Int = Int.MIN_VALUE): ShoppingCart {
        if (quantity == Int.MIN_VALUE) {
            return (addItem(product, quantity))
        }
        return (addItem(product, quantity * -1))
    }

    /**
     * Empty the cart.
     * Sets the items to an empty list and the subtotal, tax, and total to zero.
     * Synchronized to prevent multiple threads from accessing the cart at the same time.
     */
    @Synchronized
    fun empty(): ShoppingCart {
        return ShoppingCart(ConcurrentHashMap<String, ShoppingCartItem>(), 0.0, 0.0, 0.0)
    }

    private fun calculateTax(): ShoppingCart {
        return ShoppingCart(items, subtotal, (subtotal * taxPercentage).roundToCurrency(), total)
    }

    private fun calculateSubtotal(): ShoppingCart {
        return ShoppingCart(
            items,
            items.values.sumOf { it.product.price * it.quantity }.roundToCurrency(),
            tax,
            total,
        )
    }

    private fun calculateTotal(): ShoppingCart {
        return ShoppingCart(items, subtotal, tax, (subtotal + tax).roundToCurrency())
    }

    @Synchronized
    private fun calculate(): ShoppingCart {
        return calculateSubtotal().calculateTax().calculateTotal()
    }

    @Synchronized
    fun checkout(): ShoppingCart {
        return empty()
    }

    /**
     * Get the quantity of a product in the cart.
     * If the product does not exist, return zero.
     * If the product exists, return the quantity.
     * If no product is specified, return the total quantity of all products in the cart.
     * @param product The product to get the quantity of.
     * @return The quantity of the product in the cart, or zero if the product does not exist.
     */
    @Synchronized
    fun getItemCount(product: Product? = null): Int {
        if (product == null) {
            return items.values.sumOf { it.quantity }
        }
        return items.values.find { it.product == product }?.quantity ?: 0
    }

    /**
     * Get the items in the cart.
     * @return A list of the items in the cart.
     */
    @Synchronized
    fun getItems(): List<ShoppingCartItem> {
        return items.values.toList()
    }

    private fun Double.roundToCurrency() =
        BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}
