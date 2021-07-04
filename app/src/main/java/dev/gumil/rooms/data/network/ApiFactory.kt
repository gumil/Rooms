package dev.gumil.rooms.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://wetransfer.github.io/"

    fun createApi(baseUrl: String = BASE_URL) =
        createApi(baseUrl, createOkHttpClient(createLoggingInterceptor()))

    private fun createApi(baseUrl: String, okHttpClient: OkHttpClient): RoomsApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RoomsApi::class.java)

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    private fun createLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}
