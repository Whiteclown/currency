package com.bobrovskii.exchange.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ExchangeInterceptor @Inject constructor() : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()

		val newRequest = request
			.newBuilder()
			.addHeader("apikey", "HHybocE1zNJKXpIcL95tLxqgdfFuuJZT")
			.build()

		return chain.proceed(newRequest)
	}
}