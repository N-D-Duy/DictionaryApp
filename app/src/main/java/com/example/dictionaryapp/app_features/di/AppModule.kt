package com.example.dictionaryapp.app_features.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.dictionaryapp.app_features.data.local.Converters
import com.example.dictionaryapp.app_features.data.local.WordInfoDatabase
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.data.repository.RepositoryImpl
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.DeleteWordFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetAllWordFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetSingleWordInfoFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetWordLikeFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetWordSkippedFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.InsertWordToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.InsertWordsToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.UpdateWordsToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.DeleteWordFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetAllWordsFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetRandomUnusedWordsFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetSingleWordFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetWordsLikeFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.InsertSingleWordToWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.InsertWordsToWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.UpdateWordFromWordTable
import com.example.dictionaryapp.app_features.utils.GsonParser
import com.example.dictionaryapp.core_utils.download_words.DownloadWords
import com.example.dictionaryapp.core_utils.download_words.DownloadWordsImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            .fallbackToDestructiveMigration()
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
    ): Repository {
        return RepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideWordUseCase(repository: Repository): WordUseCases {
        return WordUseCases(
            getWordInfoLikeFromWordTable = GetWordsLikeFromWordTable(repository),
            getSingleWordInfoFromWordTable = GetSingleWordFromWordTable(repository),
            getAllWordFromWordTable = GetAllWordsFromWordTable(repository),
            insertWordToWordTable = InsertSingleWordToWordTable(repository),
            insertWordsToWordTable = InsertWordsToWordTable(repository),
            deleteWordFromWordTable = DeleteWordFromWordTable(repository),
            updateWordsToWordTable = UpdateWordFromWordTable(repository),
            fetchRandomUnusedWordsFromWordTable = GetRandomUnusedWordsFromWordTable(repository),

            getAllWordFromHistoryTable = GetAllWordFromHistoryTable(repository),
            getSingleWordInfoFromHistoryTable = GetSingleWordInfoFromHistoryTable(repository),
            getWordLikeFromHistoryTable = GetWordLikeFromHistoryTable(repository),
            insertWordToHistoryTable = InsertWordToHistoryTable(repository),
            insertWordsToHistoryTable = InsertWordsToHistoryTable(repository),
            deleteWordFromHistoryTable = DeleteWordFromHistoryTable(repository),
            updateWordsToHistoryTable = UpdateWordsToHistoryTable(repository),
            getWordSkippedFromHistoryTable = GetWordSkippedFromHistoryTable(repository)
        )
    }

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideDownloadWords(
        useCases: WordUseCases,
        context: Context,
        api: DictionaryApi
    ): DownloadWords {
        return DownloadWordsImpl(useCases, context, api)
    }

    @Provides
    @Singleton
    fun provideScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }
}