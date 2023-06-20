package tech.borgranch.equalkart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.borgranch.equalkart.data.local.dao.CachedProductsDao
import tech.borgranch.equalkart.data.local.dto.CachedProduct

@Database(
    entities = [
        CachedProduct::class,

    ],
    version = 1,
    exportSchema = false,
)
abstract class CachedDb : RoomDatabase() {
    abstract fun cachedProductsDao(): CachedProductsDao
}
