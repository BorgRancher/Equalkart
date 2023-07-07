package tech.borgranch.equalkart.data.local

import androidx.room.Transaction
import tech.borgranch.equalkart.data.local.dto.CachedProduct
import tech.borgranch.equalkart.data.local.dto.Order
import tech.borgranch.equalkart.data.local.dto.OrderItem
import tech.borgranch.equalkart.domain.model.ShoppingCart
import tech.borgranch.equalkart.utility.toSnakeCase
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val cachedDb: CachedDb,
) {

    suspend fun getCachedProduct(productName: String): CachedProduct? {
        return cachedDb.cachedProductsDao().getByName(productName).firstOrNull()
    }

    fun insertCachedProduct(cachedProduct: CachedProduct) {
        cachedDb.cachedProductsDao().insert(cachedProduct)
    }

    @Transaction
    /**
     * Creates an order in the database from a shopping cart.
     * @param shoppingCart The shopping cart to create an order from.
     * @return The id of the created order.
     */
    suspend fun createOrder(shoppingCart: ShoppingCart): Long = with(shoppingCart) {
        // Create initial order entity
        val order = Order(
            id = 0,
            status = OrderStatus.PENDING.toString(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            discountPercent = 0.0,
            taxPercent = taxPercentage,
            userId = 9999,
        )

        // Create order in database
        val orderId = cachedDb.ordersDao().insertWithTimeStamp(order)
        getItems().forEach { (product, quantity) ->
            // Create order item entity
            val cachedProduct = getCachedProduct(product.name.toSnakeCase()) ?: return@forEach
            val orderItem = OrderItem(
                id = 0,
                orderId = orderId,
                productId = cachedProduct.id,
                quantity = quantity,
                price = cachedProduct.price,
            )
            // Create order item in database
            cachedDb.orderItemsDao().insert(orderItem)
        }

        return orderId
    }

    fun getCachedProducts(): List<CachedProduct> {
        return cachedDb.cachedProductsDao().getAll()
    }

    fun insertCachedProducts(data: List<CachedProduct>): List<Long> {
        return cachedDb.cachedProductsDao().insertAll(data)
    }
}
