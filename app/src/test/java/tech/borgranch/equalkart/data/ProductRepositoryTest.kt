package tech.borgranch.equalkart.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import tech.borgranch.equalkart.data.local.LocalDataSource
import tech.borgranch.equalkart.data.local.dto.CachedProduct
import tech.borgranch.equalkart.data.remote.EqualResult
import tech.borgranch.equalkart.data.remote.NetworkDataSource
import tech.borgranch.equalkart.data.remote.responses.RemoteProduct

class ProductRepositoryTest {

    @Mock
    private lateinit var networkDataSource: NetworkDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productRepository = ProductRepository(networkDataSource, localDataSource)
    }

    @Test
    fun `getProduct should return cached product when available`() = runBlocking {
        val productName = "weetabix"
        val cachedProduct = CachedProduct(name = productName)
        `when`(localDataSource.getCachedProduct(productName)).thenReturn(cachedProduct)

        val result = productRepository.getProduct(productName)

        assertEquals(EqualResult.Success(cachedProduct).data, result.data)
    }

    @Test
    fun `getProduct should fetch remote product and insert into local cache when not available`() =
        runBlocking {
            val productName = "shreddies"
            val remoteProduct = RemoteProduct(name = productName)
            val cachedProduct = CachedProduct.fromRemoteProduct(remoteProduct)

            `when`(localDataSource.getCachedProduct(productName)).thenReturn(null)
            `when`(networkDataSource.getProduct(productName)).thenReturn(
                EqualResult.Success(
                    remoteProduct,
                ),
            )

            val result = productRepository.getProduct(productName)

            assertEquals(EqualResult.Success(cachedProduct).data, result.data)
        }

    @Test
    fun `getProduct should handle network errors when fetching remote product`() = runBlocking {
        val productName = "cheerios"
        val networkErrorMessage = "Network error"

        `when`(localDataSource.getCachedProduct(productName)).thenReturn(null)
        `when`(networkDataSource.getProduct(productName)).thenReturn(
            EqualResult.NetworkError(
                networkErrorMessage,
            ),
        )

        val result = productRepository.getProduct(productName)

        assertEquals(networkErrorMessage, result.message)
    }

    @Test
    fun `getProduct should handle other errors when fetching remote product`() = runBlocking {
        val productName = "cornflakes"
        val errorMessage = "Unknown error"

        `when`(localDataSource.getCachedProduct(productName)).thenReturn(null)
        `when`(networkDataSource.getProduct(productName)).thenReturn(EqualResult.Error(errorMessage))

        val result = productRepository.getProduct(productName)

        assertEquals(errorMessage, result.message)
    }
}
