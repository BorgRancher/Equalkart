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

class ShoppingCartRepositoryTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var networkDataSource: NetworkDataSource

    private lateinit var shoppingCartRepository: ShoppingCartRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        shoppingCartRepository = ShoppingCartRepository(localDataSource, networkDataSource)
    }

    @Test
    fun `addItem should add item to the shopping cart`() {
        val product = Product("Example Product", 10.0)
        val initialQuantity = 2
        val addedQuantity = 3
        initialQuantity + addedQuantity

        val initialShoppingCart =
            ShoppingCart().addItem(product, initialQuantity).addItem(product, addedQuantity)
        shoppingCartRepository.addItem(product, initialQuantity)
        shoppingCartRepository.addItem(product, addedQuantity)

        assertEquals(initialShoppingCart, shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `removeItem should remove item from the shopping cart`() {
        val product = Product("Example Product", 10.0)
        val initialQuantity = 5
        val removedQuantity = 2

        shoppingCartRepository.addItem(product, initialQuantity)

        shoppingCartRepository.removeItem(product, removedQuantity)
        val expectedShoppingCart = shoppingCartRepository.shoppingCart

        assertEquals(expectedShoppingCart, shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `clear should empty the shopping cart`() {
        val product = Product("Example Product", 10.0)
        val quantity = 3
        val initialShoppingCart = ShoppingCart().addItem(product, quantity)
        shoppingCartRepository.addItem(product, quantity)
        assertEquals(initialShoppingCart, shoppingCartRepository.shoppingCart)
        shoppingCartRepository.clear()
        assertEquals(ShoppingCart(), shoppingCartRepository.shoppingCart)
    }

    @Test
    fun `checkout should create an order and clear the shopping cart`() = runBlocking {
        val product = Product("Example Product", 10.0)
        val quantity = 2
        val orderId = 10234L

        shoppingCartRepository.addItem(product, quantity)

        `when`(localDataSource.createOrder(shoppingCartRepository.shoppingCart)).thenReturn(orderId)

        shoppingCartRepository.checkout()

        assertEquals(ShoppingCart(), shoppingCartRepository.shoppingCart)
    }
}
