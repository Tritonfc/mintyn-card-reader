package com.starter.app.ui

import com.starter.app.data.models.CardInfo

sealed class CardInfoUiState{
    data class Success(val cardInfo: CardInfo, ): CardInfoUiState()
    data class Error(val exception: Throwable): CardInfoUiState()
    object Loading : CardInfoUiState()
}
