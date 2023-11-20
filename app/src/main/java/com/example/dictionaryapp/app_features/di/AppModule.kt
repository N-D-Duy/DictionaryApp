package com.example.dictionaryapp.app_features.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.dictionaryapp.app_features.data.local.Converters
import com.example.dictionaryapp.app_features.data.local.WordInfoDatabase
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.domain.use_case.cases.GetWordHistory
import com.example.dictionaryapp.app_features.domain.use_case.cases.GetWordInfo
import com.example.dictionaryapp.app_features.domain.use_case.cases.InsertHistoryWord
import com.example.dictionaryapp.app_features.domain.use_case.cases.InsertWord
import com.example.dictionaryapp.app_features.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {
    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: DictionaryApi,
        dao: WordInfoDatabase,
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, dao)
    }
    @Provides
    @Singleton
    fun provideWordUseCase(repository: WordInfoRepository): WordUseCases {
        return WordUseCases(
            getWordInfo = GetWordInfo(repository),
            getWordHistory = GetWordHistory(repository),
            insertHistoryWord = InsertHistoryWord(repository),
            insertWord = InsertWord(repository)
        )
    }
}