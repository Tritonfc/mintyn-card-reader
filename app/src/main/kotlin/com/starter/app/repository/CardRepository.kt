package com.starter.app.repository

import com.starter.app.data.models.CardInfo
import com.starter.app.utils.Result

interface CardRepository {

    suspend fun  getCardInfo(cardNumber: String):Result<CardInfo>
}