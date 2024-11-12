package com.luke.stockwatchlist.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.luke.stockwatchlist.data.local.StockDatabase
import com.luke.stockwatchlist.data.remote.StockApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApiService {
        return Retrofit.Builder()
            .baseUrl(StockApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesStockDatabase(app: Application): StockDatabase {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE CompanyListingEntity ADD COLUMN isWatchlisted INTEGER NOT NULL DEFAULT 0")
            }
        }
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}