package tech.borgranch.equalkart.domain.model

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class ShoppingCartTest {

    private val sut = ShoppingCart()
    private val cornflakes = Product(1, "Cornflakes", 2.99, "A box of cornflakes", "cornflakes.jpg")
    private val milk = Product(2, "Milk", 1.29, "A bottle of milk", "milk.jpg")
    private val bread = Product(3, "Bread", 1.99, "A loaf of bread", "bread.jpg")
    private val eggs = Product(4, "Eggs", 2.99, "A dozen eggs", "eggs.jpg")
    private val butter = Product(5, "Butter", 1.99, "A stick of butter", "butter.jpg")
    private val cheese = Product(6, "Cheese", 2.99, "A block of cheese", "cheese.jpg")
    private val ham = Product(7, "Ham", 3.99, "A packet of ham", "ham.jpg")

    @Before
    fun setUp() {
        sut.empty()
    }

    @After
    fun tearDown() {
        sut.checkout()
    }

    @Test
    fun addItem() {
        val testAddCart = sut
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(cheese, 1)
            .addItem(ham, 2)

        assertEquals("8", testAddCart.getItemCount().toString())
        assertEquals(22.22, testAddCart.subtotal, 0.0)
    }

    @Test
    fun removeItem() {
        val testRemoveCart = sut
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(cheese, 1)
            .addItem(ham, 99)
            .removeItem(ham)

        assertTrue("Shopping cart should contain 6 items", testRemoveCart.getItemCount() == 6)
        assertEquals(14.24, testRemoveCart.subtotal, 0.0)
        assertEquals("Tax should be 2.99", 2.99, testRemoveCart.tax, 0.0)
    }

    @Test
    fun empty() {
        val testEmptyCart = sut
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(cheese, 1)
            .addItem(ham, 99)
            .empty()

        assertTrue("The shopping cart should be empty.", testEmptyCart.isEmpty)
    }

    @Test
    fun getSubtotal() {
        val testSubtotalCart = sut
            .empty()
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(cheese, 1)
            .addItem(ham, 1)

        assertEquals(18.23, testSubtotalCart.subtotal, 0.0)
    }

    @Test
    fun getTax() {
        val testTaxCart = sut
            .empty()
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(bread, 1)
            .addItem(cheese, 1)
            .addItem(ham, 1)

        assertEquals(4.25, testTaxCart.tax, 0.0)
    }

    @Test
    fun getTotal() {
        val testTotalCart = sut.addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(bread, 1)
            .addItem(cheese, 1)
            .addItem(ham, 1)

        assertEquals(testTotalCart.total, 24.47, 0.0)
    }
}
