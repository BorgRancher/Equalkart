package tech.borgranch.equalkart.data.remote

import okhttp3.logging.HttpLoggingInterceptor
import tech.borgranch.equalkart.BuildConfig

class LoggingInterceptor {
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }
}
