package tech.borgranch.equalkart.data.local

import tech.borgranch.equalkart.data.local.dto.CachedProduct

class LocalDataSource(
    private val cachedDb: CachedDb,
) {

    suspend fun getCachedProduct(productName: String): CachedProduct? {
        return cachedDb.cachedProductsDao().getByName(productName).firstOrNull()
    }

    fun insertCachedProduct(cachedProduct: CachedProduct) {
        cachedDb.cachedProductsDao().insert(cachedProduct)
    }
}
