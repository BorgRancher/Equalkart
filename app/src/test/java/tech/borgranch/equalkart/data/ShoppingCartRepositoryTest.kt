package tech.borgranch.equalkart.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.domain.model.Product
import tech.borgranch.equalkart.domain.model.ShoppingCart
import tech.borgranch.equalkart.utility.toSnakeCase

class ShoppingCartRepositoryTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var networkDataSource: NetworkDataSource

    private lateinit var shoppingCartRepository: ShoppingCartRepository

    private val product =
        Product("Example Product".toSnakeCase(), "Example Product", "An example description", 10.0)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        shoppingCartRepository = ShoppingCartRepository(localDataSource, networkDataSource)
    }

    @Test
    fun `addItem should add item to the shopping cart`() {
        val initialQuantity = 5
        val addedQuantity = 3

        val initialShoppingCart =
            ShoppingCart().addItem(product, initialQuantity).addItem(product, addedQuantity)
        shoppingCartRepository.addItem(product, initialQuantity)
        shoppingCartRepository.addItem(product, addedQuantity)

        assertEquals(initialShoppingCart, shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `removeItem should remove item from the shopping cart`() {
        val initialQuantity = 5
        val removedQuantity = 2

        shoppingCartRepository.addItem(product, initialQuantity)

        shoppingCartRepository.removeItem(product, removedQuantity)
        val expectedShoppingCart = shoppingCartRepository.shoppingCart

        assertEquals(expectedShoppingCart, shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `clear should empty the shopping cart`() {
        val quantity = 3
        val initialShoppingCart = ShoppingCart().addItem(product, quantity)
        shoppingCartRepository.addItem(product, quantity)
        assertEquals(initialShoppingCart, shoppingCartRepository.shoppingCart)
        shoppingCartRepository.clearCart()
        assertEquals(ShoppingCart(), shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `checkout should create an order and clear the shopping cart`() = runBlocking {
        val quantity = 2
        val orderId = 10234L

        shoppingCartRepository.addItem(product, quantity)

        `when`(localDataSource.createOrder(shoppingCartRepository.shoppingCart)).thenReturn(orderId)

        shoppingCartRepository.checkoutCart()

        assertEquals(ShoppingCart(), shoppingCartRepository.shoppingCart)
    }
}
