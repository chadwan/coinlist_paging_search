package com.ccwo.wongnaiassingment.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {
    @GET("coins")
    fun getCoins(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<CoinApiResponse>

    @GET("coins")
    fun getCoinsSearch(@Query("prefix") prefix: String): Call<CoinApiResponse>

    companion object {
        private const val BASE_URL = "https://api.coinranking.com/v1/public/"
        fun create(): CoinApi {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(BASE_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinApi::class.java)
        }
    }
}