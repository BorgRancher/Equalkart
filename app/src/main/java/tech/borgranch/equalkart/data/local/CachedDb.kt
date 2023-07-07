package tech.borgranch.equalkart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: CachedDb? = null
        private const val DATABASE_NAME = "cached.db"
        operator fun invoke(appContext: Context): CachedDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    CachedDb::class.java,
                    DATABASE_NAME,
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun cachedProductsDao(): CachedProductsDao
    abstract fun ordersDao(): OrderDao
    abstract fun orderItemsDao(): OrderItemDao
}
