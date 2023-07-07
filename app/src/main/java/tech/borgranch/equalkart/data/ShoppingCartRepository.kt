@file:Suppress("unused")

package tech.borgranch.equalkart.data

import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.model.ShoppingCart
import timber.log.Timber
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource,
) {
    private var _shoppingCart: ShoppingCart = ShoppingCart()
    val shoppingCart: ShoppingCart
        get() = _shoppingCart.copy()

    fun addItem(product: Product, quantity: Int) {
        _shoppingCart = _shoppingCart.addItem(product, quantity)
    }

    fun removeItem(product: Product, quantity: Int) {
        _shoppingCart = shoppingCart.removeItem(product, quantity)
    }

    fun clearCart() {
        _shoppingCart = _shoppingCart.empty()
    }

    suspend fun checkoutCart(): Long {
        // Create order in database
        val orderId = localDataSource.createOrder(_shoppingCart)
        Timber.d("Created order with id $orderId")
        clearCart()
        return orderId
    }
}
