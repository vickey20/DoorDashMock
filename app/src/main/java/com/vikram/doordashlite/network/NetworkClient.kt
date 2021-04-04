package com.vikram.doordashlite.network

import com.vikram.doordashlite.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *   Created by vikram.gupta on 4/4/21.
 */
object NetworkClient {
    private var retrofitClient =
        retrofitClient(
            okHttpClient(),
            GsonConverterFactory.create(),
            RxJava3CallAdapterFactory.create()
        )

    private fun okHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging).build()
    }

    private fun retrofitClient(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    fun <T> getApiServiceFor(service: Class<T>): T {
        return retrofitClient.create(service)
    }
}