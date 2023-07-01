package tech.borgranch.equalkart.data

import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.local.dto.CachedProduct
import tech.borgranch.equalkart.data.remote.EqualResult
import tech.borgranch.equalkart.data.remote.NetworkDataSource

class ProductRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
) {

    suspend fun getProduct(productName: String): EqualResult<CachedProduct> {
        val cachedProduct = localDataSource.getCachedProduct(productName)
        return if (cachedProduct != null) {
            EqualResult.Success(cachedProduct)
        } else {
            return fetchAndInsertRemoteProduct(productName)
        }
    }

    private suspend fun fetchAndInsertRemoteProduct(productName: String): EqualResult<CachedProduct> {
        return when (val productResponse = networkDataSource.getProduct(productName)) {
            is EqualResult.Success -> {
                val product = CachedProduct.fromRemoteProduct(productResponse.data!!)
                localDataSource.insertCachedProduct(product)
                EqualResult.Success(product)
            }

            is EqualResult.NetworkError -> {
                EqualResult.NetworkError(message = productResponse.message ?: "Unknown error")
            }

            is EqualResult.Error -> {
                EqualResult.Error(message = productResponse.message ?: "Unknown error")
            }
        }
    }
}
