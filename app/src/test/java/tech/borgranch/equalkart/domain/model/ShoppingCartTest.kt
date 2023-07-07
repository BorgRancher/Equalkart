package tech.borgranch.equalkart.domain.model

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import tech.borgranch.equalkart.fake.TestDoubles
import tech.borgranch.equalkart.utility.roundToCurrency

class ShoppingCartTest {

    private val doubles = TestDoubles()
    private val products = doubles.products
    private val sut = ShoppingCart()

    private val cornflakes = products["cornflakes"]!!
    private val cheerios = products["cheerios"]!!
    private val shreddies = products["shreddies"]!!
    private val frosties = products["frosties"]!!
    private val weetabix = products["weetabix"]!!

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
        try {
            val testAddCart = sut
                .addItem(cornflakes, 1)
                .addItem(cheerios, 1)
                .addItem(shreddies, 1)
                .addItem(frosties, 1)
                .addItem(weetabix, 1)

            val expectedSubTotal = listOf(
                1 * cornflakes.price,
                1 * cheerios.price,
                1 * shreddies.price,
                1 * frosties.price,
                1 * weetabix.price,
            ).sum().roundToCurrency()

            assertEquals(5, testAddCart.getItemCount())
            assertEquals(expectedSubTotal, testAddCart.subtotal, 0.0)
        } catch (e: Exception) {
            fail("Exception thrown: ${e.message}")
        }
    }

    @Test
    fun `items can be removed from the cart`() {
        val testRemoveCart = sut
            .addItem(cornflakes, 1)
            .addItem(cheerios, 1)
            .addItem(shreddies, 2)
            .addItem(frosties, 1)
            .addItem(weetabix, 1)
            .addItem(weetabix, 99)
            .removeItem(weetabix)

        val expectedSubTotal = listOf(
            1 * cornflakes.price,
            1 * cheerios.price,
            2 * shreddies.price,
            1 * frosties.price,
        ).sum().roundToCurrency()

        assertTrue("Shopping cart should contain 5 items", testRemoveCart.getItemCount() == 5)
        assertEquals(
            "Subtotal should be $expectedSubTotal",
            expectedSubTotal,
            testRemoveCart.subtotal,
            0.0,
        )
        assertEquals("There should be 1 cornflakes", 1, testRemoveCart.getItemCount(cornflakes))
    }

    @Test
    fun `the cart can be emptied`() {
        val testEmptyCart = sut
            .addItem(cornflakes, 1)
            .addItem(cheerios, 1)
            .addItem(shreddies, 1)
            .addItem(frosties, 1)
            .addItem(weetabix, 1)
            .empty()

        assertTrue("The shopping cart should be empty.", testEmptyCart.isEmpty)
    }

    @Test
    fun `we can calculate the sub-total correctly`() {
        val testSubtotalCart = sut
            .empty()
            .addItem(cornflakes, 1)
            .addItem(cheerios, 1)
            .addItem(shreddies, 1)
            .addItem(frosties, 1)
            .addItem(weetabix, 1)

        val expectedSubTotal = listOf(
            1 * cornflakes.price,
            1 * cheerios.price,
            1 * shreddies.price,
            1 * frosties.price,
            1 * weetabix.price,
        ).sum().roundToCurrency()

        assertEquals(expectedSubTotal, testSubtotalCart.subtotal, 0.0)
    }

    @Test
    fun `taxes are calculated correctly`() {
        val testTaxCart = sut
            .empty()
            .addItem(cornflakes, 1)
            .addItem(cheerios, 1)
            .addItem(shreddies, 1)
            .addItem(frosties, 1)
            .addItem(weetabix, 1)
            .addItem(shreddies, 1)

        val expectedTaxes = listOf(
            1 * cornflakes.price,
            1 * cheerios.price,
            1 * shreddies.price,
            1 * frosties.price,
            1 * weetabix.price,
            1 * shreddies.price,
        ).sum().times(sut.effectiveTaxPercentage).roundToCurrency()

        assertEquals(expectedTaxes, testTaxCart.tax, 0.0)
    }

    @Test
    fun `the grand total is calculated correctly`() {
        val testTotalCart = sut.addItem(cornflakes, 1)
            .addItem(cheerios, 1)
            .addItem(shreddies, 1)
            .addItem(frosties, 1)
            .addItem(weetabix, 1)
            .addItem(shreddies, 1)

        val expectedGrandTotal = listOf(
            1 * cornflakes.price,
            1 * cheerios.price,
            1 * shreddies.price,
            1 * frosties.price,
            1 * weetabix.price,
            1 * shreddies.price,
        ).sum().times(1 + sut.effectiveTaxPercentage).roundToCurrency()

        assertEquals(
            "The total for this shopping cart should be $expectedGrandTotal",
            expectedGrandTotal,
            testTotalCart.total,
            0.0,
        )
    }

    @Test
    fun `removing a non-existent item from the cart should not affect the calculations`() {
        val testRemoveNonExistentCart = sut.removeItem(cheerios, 999)
            .addItem(frosties, 2)
            .removeItem(shreddies, 88888)

        assertEquals(2, testRemoveNonExistentCart.getItemCount())
    }
}
