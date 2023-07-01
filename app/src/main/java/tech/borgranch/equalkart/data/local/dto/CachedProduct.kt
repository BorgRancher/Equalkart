package tech.borgranch.equalkart.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.borgranch.equalkart.data.local.ProductsMetaCache
import tech.borgranch.equalkart.data.remote.responses.RemoteProduct
import tech.borgranch.equalkart.utility.toSnakeCase

@Entity(tableName = "products")
data class CachedProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sku: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val inStock: Boolean = false,
) {
    val searchTerm get() = name.toSnakeCase()
    companion object {
        fun fromRemoteProduct(remoteProduct: RemoteProduct): CachedProduct {
            val productMeta = ProductsMetaCache.getProductMeta(remoteProduct.name)

            return CachedProduct(
                id = 0,
                name = remoteProduct.name,
                price = remoteProduct.price,
                sku = productMeta.sku,
                description = productMeta.description,
                image = productMeta.image,
                inStock = true,
            )
        }
    }
}
