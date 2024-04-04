package com.starter.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starter.app.repository.CardRepository
import com.starter.app.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(private val cardRepository: CardRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<CardInfoUiState>(CardInfoUiState.Loading)
    val uiState: StateFlow<CardInfoUiState> = _uiState.asStateFlow()


    fun getCardInfo(cardNumber: String) {
        viewModelScope.launch {
            val result = cardRepository.getCardInfo(cardNumber)
            when (result) {
                is Result.Success -> {
                    _uiState.value = CardInfoUiState.Success(result.data)

                }

                is Result.Error -> {
                    _uiState.value = result.error.takeUnless { it is CancellationException }
                        ?.let(CardInfoUiState::Error)
                        ?: CardInfoUiState.Loading


                }
            }
        }

    }
}