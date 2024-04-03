package com.starter.app.repository

import com.starter.app.data.models.CardInfo
import com.starter.app.data.remote.CardApi
import com.starter.app.utils.Result
import com.starter.app.utils.runCatching

class CardRepositoryImpl(private val cardApi:CardApi):CardRepository {
    override suspend fun getCardInfo(cardNumber: String): Result<CardInfo> {
        return runCatching {
            cardApi.getCardInfo(cardNumber)
        }
    }
}