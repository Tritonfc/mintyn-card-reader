package com.starter.app.data.remote

import com.starter.app.data.models.CardInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApi {



    @GET("{number}")
    suspend fun getCardInfo(@Path("number") number: String): CardInfo
}