package com.bobrovskii.currency.di

import android.content.Context
import androidx.room.Room
import com.bobrovskii.exchange.data.api.ExchangeApi
import com.bobrovskii.exchange.data.dao.CurrencyNamesDao
import com.bobrovskii.exchange.data.database.ExchangeDataBase
import com.bobrovskii.exchange.data.datasource.CurrencyNamesLocalDataSource
import com.bobrovskii.exchange.data.datasource.CurrencyNamesLocalDataSourceImpl
import com.bobrovskii.exchange.data.datasource.CurrencyNamesRemoteDataSource
import com.bobrovskii.exchange.data.datasource.CurrencyNamesRemoteDataSourceImpl
import com.bobrovskii.exchange.data.interceptor.ExchangeInterceptor
import com.bobrovskii.exchange.data.repository.ExchangeRepositoryImpl
import com.bobrovskii.exchange.data.repository.SettingsRepositoryImpl
import com.bobrovskii.exchange.domain.repository.ExchangeRepository
import com.bobrovskii.exchange.domain.repository.SettingsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

	private val contentType = "application/json".toMediaType()

	private val json = Json {
		ignoreUnknownKeys = true
	}

	@Provides
	@Singleton
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
		HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

	@Provides
	@Singleton
	fun provideOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		exchangeInterceptor: ExchangeInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(exchangeInterceptor)
			.build()

	@Provides
	@Singleton
	fun provideRetrofit(client: OkHttpClient): Retrofit =
		Retrofit
			.Builder()
			.baseUrl("https://api.apilayer.com/")
			.addConverterFactory(json.asConverterFactory(contentType))
			.client(client)
			.build()

	@Provides
	@Singleton
	fun provideExchangeApi(retrofit: Retrofit): ExchangeApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideExchangeDataBase(@ApplicationContext context: Context): ExchangeDataBase =
		Room.databaseBuilder(
			context,
			ExchangeDataBase::class.java,
			"ExchangeDataBase"
		).build()

	@Provides
	@Singleton
	fun provideCurrencyNamesDao(database: ExchangeDataBase): CurrencyNamesDao =
		database.currencyNamesDao()

	@Provides
	@Singleton
	fun provideLocalDataSource(dao: CurrencyNamesDao): CurrencyNamesLocalDataSource =
		CurrencyNamesLocalDataSourceImpl(dao)

	@Provides
	@Singleton
	fun provideRemoteDataSource(api: ExchangeApi): CurrencyNamesRemoteDataSource =
		CurrencyNamesRemoteDataSourceImpl(api)

	@Provides
	@Singleton
	fun provideExchangeRepository(
		localDataSource: CurrencyNamesLocalDataSource,
		remoteDataSource: CurrencyNamesRemoteDataSource,
		api: ExchangeApi,
	): ExchangeRepository =
		ExchangeRepositoryImpl(localDataSource, remoteDataSource, api)

	@Provides
	@Singleton
	fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository =
		SettingsRepositoryImpl(context)
}