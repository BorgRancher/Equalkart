package tech.borgranch.equalkart.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.borgranch.equalkart.data.local.CachedDb
import tech.borgranch.equalkart.data.local.LocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalData {
    @Provides
    @Singleton
    fun provideCachedDb(@ApplicationContext appContext: Context): CachedDb {
        return CachedDb(appContext = appContext)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(cachedDb: CachedDb): LocalDataSource {
        return LocalDataSource(cachedDb = cachedDb)
    }
}
