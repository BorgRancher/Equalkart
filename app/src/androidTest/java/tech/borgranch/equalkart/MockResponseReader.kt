package tech.borgranch.equalkart

import android.content.Context
import com.google.gson.Gson
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.InputStream

class MockResponseReader(path: String, appContext: Context) {
    var content: String = ""
    lateinit var inputStream: InputStream

    init {
        try {
            inputStream = appContext.resources.openRawResource(
                appContext.resources.getIdentifier(
                    /* name = */ path,
                    /* defType = */ "raw",
                    /* defPackage = */ appContext.packageName,
                ),
            )
            content = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Timber.e("Error reading mock response: ${e.localizedMessage}")
            content = ""
        } finally {
            inputStream.close()
        }
    }

    inline fun <reified T> getResponse(): Response<T>? {
        return if (content.trim().isNotEmpty()) {
            val result: T = Gson().fromJson(
                /* json = */ content,
                /* classOfT = */ T::class.java,
            )
            return Response.success(
                result,
            )
        } else {
            Response.error(
                /* code = */ 400,
                /* body = */ "Product not found".toResponseBody(contentType = null),
            )
        }
    }
}
