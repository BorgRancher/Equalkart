package tech.borgranch.equalkart.domain.model

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class ShoppingCartTest {

    private val sut = ShoppingCart()
    private val cornflakes = Product("Cornflakes", 2.99)
    private val milk = Product("Milk", 1.29)
    private val bread = Product("Bread", 1.99)
    private val eggs = Product("Eggs", 2.99)
    private val butter = Product("Butter", 1.99)
    private val cheese = Product("Cheese", 2.99)
    private val ham = Product("Ham", 3.99)

    @Before
    fun setUp() {
        sut.empty()
    }

    @After
    fun tearDown() {
        sut.checkout()
    }

    @Test
    fun `items can be added to the cart`() {
        val testAddCart = sut
            .addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(cheese, 1)
            .addItem(ham, 2)

        assertEquals(8, testAddCart.getItemCount())
        assertEquals(22.22, testAddCart.subtotal, 0.0)
    }

    @Test
    fun `items can be removed from the cart`() {
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
        assertEquals("Tax should be 1.78", 1.78, testRemoveCart.tax, 0.0)
    }

    @Test
    fun `the cart can be emptied`() {
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
    fun `we can calculate the sub-total correctly`() {
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
    fun `taxes are calculated correctly`() {
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

        assertEquals(2.53, testTaxCart.tax, 0.0)
    }

    @Test
    fun `the grand total is calculated correctly`() {
        val testTotalCart = sut.addItem(cornflakes, 1)
            .addItem(milk, 1)
            .addItem(bread, 1)
            .addItem(eggs, 1)
            .addItem(butter, 1)
            .addItem(bread, 1)
            .addItem(cheese, 1)
            .addItem(ham, 1)

        assertEquals(
            "The total for this shopping cart should be 22.75",
            22.75,
            testTotalCart.total,
            0.0,
        )
    }

    @Test
    fun `removing a non-existent item from the cart should not affect the calculations`() {
        val testRemoveNonExistentCart = sut.removeItem(milk, 999)
            .addItem(eggs, 2)
            .removeItem(bread, 88888)

        assertEquals(2, testRemoveNonExistentCart.getItemCount())
    }
}
