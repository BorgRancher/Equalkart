package tech.borgranch.equalkart.fake

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tech.borgranch.equalkart.domain.model.Product
import java.lang.reflect.Type

class TestDoubles {
    private val jsonString = """
        {
          "cornflakes": {
            "sku": "sku-125",
            "name": "Cornflakes",
            "description": "Corn flakes, or cornflakes, is a breakfast cereal made from toasted flakes of corn.",
            "price": 2.99,
            "image": "https://www.kelloggs.co.uk/content/dam/europe/kelloggs_gb/en_GB/product/product-shot/cornflakes-cereal-720x407.png",
            "inStock": true
          },
          "cheerios": {
            "sku": "sku-123",
            "name": "Cheerios",
            "description": "Cheerios is an American brand of cereal manufactured by General Mills, consisting of pulverized oats in the shape of a solid torus.",
            "price": 1.29,
            "image": "https://www.cheerios.com/~/media/cheerios/images/products/cheerios/cheerios-product-og.ashx",
            "inStock": true
          },
          "shreddies": {
            "sku": "sku-124",
            "name": "Shreddies",
            "description": "Shreddies is a breakfast cereal made from lattices of wholegrain wheat.",
            "price": 9.89,
            "image": "https://www.nestle-cereals.com/uk/sites/g/files/qirczx341/files/styles/product_image/public/shreddies-original.png?itok\u003dZ3Z3Z3Z3",
            "inStock": true
          },
          "frosties": {
            "sku": "sku-126",
            "name": "Frosties",
            "description": "Frosties is a breakfast cereal, produced by the Kellogg Company and consisting of sugar-coated corn flakes.",
            "price": 2.99,
            "image": "https://www.kelloggs.co.uk/content/dam/europe/kelloggs_gb/en_GB/product/product-shot/frosties-cereal-720x407.png",
            "inStock": true
          },
          "weetabix": {
            "sku": "sku-127",
            "name": "Weetabix",
            "description": "Weetabix is a whole grain wheat breakfast cereal produced by Weetabix Limited in the United Kingdom.",
            "price": 8.98,
            "image": "https://www.weetabix.co.uk/~/media/weetabix-responsive/products/weetabix/weetabix-product-og.ashx",
            "inStock": true
          }
        }

    """.trimIndent()

    val products: Map<String, Product>

    init {
        val gson = Gson()
        val type: Type = object : TypeToken<Map<String, Product>>() {}.type
        products = gson.fromJson(jsonString, type)
    }
}
