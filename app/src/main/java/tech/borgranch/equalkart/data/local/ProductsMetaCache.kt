@file:Suppress("MaxLineLength")

package tech.borgranch.equalkart.data.local

data class ProductMeta(
    val sku: String,
    val name: String,
    val image: String,
    val description: String,
)

object ProductsMetaCache {

    fun getProductMeta(productName: String): ProductMeta {
        return allProductMetas()[productName]
            ?: throw IllegalArgumentException("Product not found")
    }

    private fun allProductMetas(): Map<String, ProductMeta> {
        return mapOf(
            "cheerios" to ProductMeta(
                sku = "sku-123",
                name = "Cheerios",
                image = "https://www.cheerios.com/~/media/cheerios/images/products/cheerios/cheerios-product-og.ashx",
                description = "Cheerios is an American brand of cereal manufactured by General Mills, consisting of pulverized oats in the shape of a solid torus.",
            ),

            "shreddies" to ProductMeta(
                "sku-124",
                "Shreddies",
                "https://www.nestle-cereals.com/uk/sites/g/files/qirczx341/files/styles/product_image/public/shreddies-original.png?itok=Z3Z3Z3Z3",
                "Shreddies is a breakfast cereal made from lattices of wholegrain wheat.",
            ),

            "cornflakes" to ProductMeta(
                "sku-125",
                "Corn Flakes",
                "https://www.kelloggs.co.uk/content/dam/europe/kelloggs_gb/en_GB/product/product-shot/cornflakes-cereal-720x407.png",
                "Corn flakes, or cornflakes, is a breakfast cereal made from toasted flakes of corn.",
            ),

            "frosties" to ProductMeta(
                "sku-126",
                "Frosties",
                "https://www.kelloggs.co.uk/content/dam/europe/kelloggs_gb/en_GB/product/product-shot/frosties-cereal-720x407.png",
                "Frosties is a breakfast cereal, produced by the Kellogg Company and consisting of sugar-coated corn flakes.",
            ),

            "weetabix" to ProductMeta(
                "sku-127",
                "Weetabix",
                "https://www.weetabix.co.uk/~/media/weetabix-responsive/products/weetabix/weetabix-product-og.ashx",
                "Weetabix is a whole grain wheat breakfast cereal produced by Weetabix Limited in the United Kingdom.",
            ),
        )
    }
}
