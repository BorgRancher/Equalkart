package tech.borgranch.equalkart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import tech.borgranch.equalkart.data.local.dto.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAll(): List<Order>

    @Query("SELECT * FROM orders WHERE id = :id")
    fun getById(id: Int): Order

    @Insert(entity = Order::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: Order): Long

    suspend fun insertWithTimeStamp(order: Order): Long {
        val currentTime = System.currentTimeMillis()
        val newOrder = order.copy(createdAt = currentTime, updatedAt = currentTime)
        return insert(newOrder)
    }

    @Insert(entity = Order::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(orders: List<Order>)

    fun updateWithTimeStamp(order: Order) {
        val currentTime = System.currentTimeMillis()
        val newOrder = order.copy(updatedAt = currentTime)
        update(newOrder)
    }

    @Update(entity = Order::class, onConflict = OnConflictStrategy.REPLACE)
    fun update(order: Order)

    @Update(entity = Order::class, onConflict = OnConflictStrategy.REPLACE)
    fun update(orders: List<Order>)

    @Delete(entity = Order::class)
    fun delete(order: Order)

    @Delete(entity = Order::class)
    fun delete(orders: List<Order>)

    @Query("DELETE FROM orders")
    fun deleteAll()
}
