package com.bobrovskii.currency.di

import com.bobrovskii.currency.navigation.Navigator
import com.bobrovskii.favorite.presentation.FavoriteRouter
import com.bobrovskii.popular.presentation.PopularRouter
import com.bobrovskii.sorting.presentation.SortingRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

	@Provides
	@Singleton
	fun provideNavigator(): Navigator =
		Navigator()

	@Provides
	@Singleton
	fun provideFavoriteRouter(navigator: Navigator): FavoriteRouter =
		navigator

	@Provides
	@Singleton
	fun providePopularRouter(navigator: Navigator): PopularRouter =
		navigator

	@Provides
	@Singleton
	fun provideSortingRouter(navigator: Navigator): SortingRouter =
		navigator
}