package tech.borgranch.equalkart.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ProductTest {

    @Test
    fun `product names can be edited`() {
        val baseProduct = Product.empty().editName("Cornflakes").editPrice(3.99)
        val testProduct = baseProduct.editName("Corn Flakes")
        assertEquals("Corn Flakes", testProduct.name)
        assertEquals(baseProduct.price, testProduct.price, 0.0)
    }

    @Test
    fun `blank product names are not permitted`() {
        val baseProduct = Product.empty().editName("Cornflakes").editPrice(3.99)
        try {
            baseProduct.editName("")
            fail("Blank product names should not be permitted")
        } catch (e: IllegalArgumentException) {
            assertEquals("Name must not be blank", e.message)
        }
    }

    @Test
    fun `product prices can be edited`() {
        val baseProduct = Product.empty().editName("Cornflakes").editPrice(3.99)
        val testProduct = baseProduct.editPrice(2.99)
        assertEquals(baseProduct.name, testProduct.name)
        assertEquals(2.99, testProduct.price, 0.0)
    }

    @Test
    fun `negative product prices are not permitted`() {
        val baseProduct = Product.empty().editName("Cornflakes").editPrice(3.99)
        try {
            baseProduct.editPrice(-1.0)
            fail("Negative product prices should not be permitted")
        } catch (e: IllegalArgumentException) {
            assertEquals("Price must be non-negative", e.message)
        }
    }
}
