@file:Suppress("MaxLineLength")

package tech.borgranch.equalkart.data.local

import tech.borgranch.equalkart.utility.toSnakeCase
import timber.log.Timber

data class ProductMeta(
    val sku: String,
    val name: String,
    val image: String,
    val description: String,
) {
    companion object {
        fun empty(): ProductMeta {
            return ProductMeta(
                sku = "",
                name = "",
                image = "",
                description = "",
            )
        }
    }
}

object ProductsMetaCache {

    fun getProductMeta(productName: String): ProductMeta {
        val productMeta: ProductMeta? =
            allProductMetas().getOrDefault(productName.toSnakeCase(), null)
        return if (productMeta != null) {
            Timber.d("Product found: ${productName.toSnakeCase()}")
            productMeta
        } else {
            Timber.d("Product not found: ${productName.toSnakeCase()}")
            ProductMeta.empty()
        }
    }

    private fun allProductMetas(): Map<String, ProductMeta> {
        return mapOf(
            "cheerios" to ProductMeta(
                sku = "sku-123",
                name = "Cheerios",
                image = "https://www.cheerios.com/_next/image?url=https%3A%2F%2Fcheerioscmsprd.wpengine.com%2Fwp-content%2Fuploads%2F2021%2F11%2FOriginal-Cheerios_460x460.png&w=1920&q=75",
                description = "Cheerios is an American brand of cereal manufactured by General Mills, consisting of pulverized oats in the shape of a solid torus.",
            ),

            "shreddies" to ProductMeta(
                "sku-124",
                "Shreddies",
                "https://cdn.webshopapp.com/shops/263312/files/347134403/nestle-nestle-frosted-shreddies-500g.jpg",
                "Shreddies is a breakfast cereal made from lattices of wholegrain wheat.",
            ),

            "corn_flakes" to ProductMeta(
                "sku-125",
                "Corn Flakes",
                "https://m.media-amazon.com/images/I/81LNVBwSQmL._SL1500_.jpg",
                "Corn flakes, or cornflakes, is a breakfast cereal made from toasted flakes of corn.",
            ),

            "frosties" to ProductMeta(
                "sku-126",
                "Frosties",
                "https://m.media-amazon.com/images/I/71esoaOK1wL.jpg",
                "Frosties is a breakfast cereal, produced by the Kellogg Company and consisting of sugar-coated corn flakes.",
            ),

            "weetabix" to ProductMeta(
                "sku-127",
                "Weetabix",
                "https://weetabixea.com/wp-content/uploads/2020/01/Original-450g-Mockup-2.png",
                "Weetabix is a whole grain wheat breakfast cereal produced by Weetabix Limited in the United Kingdom.",
            ),
        )
    }
}
