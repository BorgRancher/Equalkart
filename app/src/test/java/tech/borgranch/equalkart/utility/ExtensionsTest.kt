package tech.borgranch.equalkart.utility

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `it should convert sentences to snake_case for search terms`() {
        assertEquals("hello_world", "Hello World".toSnakeCase())
    }

    @Test
    fun `it should round currency amounts up or down appropriately`() {
        assertEquals(1.23, 1.234.roundToCurrency(), 0.0)
    }

    @Test
    fun `it should capitalize words for product names`() {
        assertEquals("Hello World", "hello world".capitalizeWords())
    }
}
