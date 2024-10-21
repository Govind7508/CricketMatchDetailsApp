package com.example.eclipticongovindtest.di

import android.content.Context
import androidx.room.Room
import com.example.eclipticongovindtest.auth.data.UserDAO
import com.example.eclipticongovindtest.auth.data.UserRoomDataBase
import com.example.eclipticongovindtest.home.data.network.MatchDetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.eclipticongovindtest.home.data.network.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMatchApi(
        remoteDataSource: RemoteDataSource
    ): MatchDetailsApi {
        return remoteDataSource.buildApi(MatchDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserRoomDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserRoomDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserRoomDataBase): UserDAO {
        return database.userDao()
    }
}