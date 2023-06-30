package tech.borgranch.equalkart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.borgranch.equalkart.data.local.dao.CachedProductsDao
import tech.borgranch.equalkart.data.local.dao.OrderDao
import tech.borgranch.equalkart.data.local.dao.OrderItemDao
import tech.borgranch.equalkart.data.local.dto.CachedProduct
import tech.borgranch.equalkart.data.local.dto.Order
import tech.borgranch.equalkart.data.local.dto.OrderItem

@Database(
    entities = [
        CachedProduct::class,
        Order::class,
        OrderItem::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class CachedDb : RoomDatabase() {
    abstract fun cachedProductsDao(): CachedProductsDao
    abstract fun ordersDao(): OrderDao
    abstract fun orderItemsDao(): OrderItemDao
}
