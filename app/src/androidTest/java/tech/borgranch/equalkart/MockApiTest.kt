package tech.borgranch.equalkart

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tech.borgranch.equalkart.data.remote.responses.ProductResponse

class MockApiTest {

    private val appContext: Context =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    @Test
    fun translateJsonToProductResponse() {
        val serverCheerios =
            MockResponseReader("cheerios", appContext).getResponse<ProductResponse>()

        val localCheerios = ProductResponse(
            "Cheerios",
            8.43,
        )

        assertTrue(serverCheerios!!.isSuccessful)
        assertTrue(serverCheerios.body() is ProductResponse)
        assertTrue(serverCheerios.body() == localCheerios)

        val serverCornFlakes =
            MockResponseReader("cornflakes", appContext).getResponse<ProductResponse>()
        val localCornflakes = ProductResponse(
            "Corn Flakes",
            2.52,
        )

        assertTrue(serverCornFlakes!!.isSuccessful)
        assertTrue(serverCornFlakes.body() is ProductResponse)
        assertTrue(serverCornFlakes.body() == localCornflakes)

        val serverMilk = MockResponseReader("milk", appContext).getResponse<ProductResponse>()

        val localMilk = ProductResponse(
            "Milk",
            1.29,
        )

        assertTrue(serverMilk!!.code() == 400)
        assertFalse(serverMilk.body() is ProductResponse)
        assertFalse(serverMilk.body() == localMilk)
        assertTrue(serverMilk.errorBody()!!.string() == "Product not found")
        assertTrue(serverMilk.errorBody()!!.contentType() == null)
    }
}
