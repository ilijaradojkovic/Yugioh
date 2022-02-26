package com.example.yugioh.DI

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yugioh.Constants.BASE_URL
import com.example.yugioh.Constants.DATABASE_NAME
import com.example.yugioh.data.remote.responses.YiguihAPI
import com.example.yugioh.repository.YugiohRepository
import com.example.yugioh.room.Dao
import com.example.yugioh.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// kaze da ovaj module koji bukv fabrika za klase i to ziiv koliko i singelton tj kao cela app
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):Database {
      return  Room.databaseBuilder(
            context, Database::class.java,
            DATABASE_NAME
        ).build()
    }



    @Provides
    @Singleton
    fun provideDao(database: Database):Dao{
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideYugiohReposiroty(api:YiguihAPI,dao:Dao) = YugiohRepository(api,dao)


    @Provides
    @Singleton
    fun provideAPI() = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(YiguihAPI::class.java)


}