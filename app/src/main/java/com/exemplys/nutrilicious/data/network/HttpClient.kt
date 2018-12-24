package com.exemplys.nutrilicious.data.network

import com.exemplys.nutrilicious.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL = "https://api.nal.usda.gov/ndb/"
private val usdaClient by lazy { buildClient() }
val usdaApi: UsdaApi by lazy { usdaClient.create(UsdaApi::class.java) }  // Public

private fun buildClient(): Retrofit = Retrofit.Builder()   // Builds Retrofit object
    .baseUrl(BASE_URL)
    .client(buildHttpClient())
    .addConverterFactory(MoshiConverterFactory.create())  // Uses Moshi for JSON
    .build()

private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor())  // Logs API responses to Logcat
    .addInterceptor(apiKeyInterceptor())   // Adds API key to request URLs
    .build()

private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY  // Only does logging in debug mode
    } else {
        HttpLoggingInterceptor.Level.NONE  // Otherwise no logging
    }
}


private fun injectQueryParams(
    vararg params: Pair<String, String>
): Interceptor = Interceptor { chain ->

    val originalRequest = chain.request()
    val urlWithParams = originalRequest.url().newBuilder()
        .apply { params.forEach { addQueryParameter(it.first, it.second) } }
        .build()
    val newRequest = originalRequest.newBuilder().url(urlWithParams).build()

    chain.proceed(newRequest)
}

private fun apiKeyInterceptor() = injectQueryParams(
    "api_key" to API_KEY
)

