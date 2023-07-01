@file:Suppress("TooManyFunctions")

package tech.borgranch.equalkart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import tech.borgranch.equalkart.data.local.dto.OrderItem

@Dao
interface OrderItemDao {

    @Query("SELECT * FROM order_items")
    fun getAll(): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE id = :id")
    fun getById(id: Int): OrderItem

    @Insert(entity = OrderItem::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderItem: OrderItem): Long

    @Insert(entity = OrderItem::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderItems: List<OrderItem>)

    @Update(entity = OrderItem::class, onConflict = OnConflictStrategy.REPLACE)
    fun update(orderItem: OrderItem)

    @Update(entity = OrderItem::class, onConflict = OnConflictStrategy.REPLACE)
    fun update(orderItems: List<OrderItem>)

    @Delete(entity = OrderItem::class)
    fun delete(orderItem: OrderItem)

    @Delete(entity = OrderItem::class)
    fun delete(orderItems: List<OrderItem>)

    @Query("DELETE FROM order_items")
    fun deleteAll()

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getByOrderId(orderId: Int): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE productId = :productId")
    fun getByProductId(productId: Int): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE quantity = :quantity")
    fun getByQuantity(quantity: Int): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE price = :price")
    fun getByPrice(price: Double): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE price * quantity = :total")
    fun getByTotal(total: Double): List<OrderItem>
}
