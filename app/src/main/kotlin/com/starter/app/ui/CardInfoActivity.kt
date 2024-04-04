package com.starter.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.starter.app.R
import androidx.lifecycle.Lifecycle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.starter.app.MainActivity
import com.starter.app.data.models.CardInfo
import com.starter.app.databinding.ActivityCardInfoBinding
import com.starter.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardInfoActivity : AppCompatActivity() {

    private lateinit var cardInfoActivityBinding: ActivityCardInfoBinding

    private val viewModel by viewModels<CardInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardInfoActivityBinding = ActivityCardInfoBinding.inflate(layoutInflater)
        val view = cardInfoActivityBinding.root
        setContentView(view)

        val cardNumber = intent.getStringExtra(MainActivity.BUNDLE_KEY)

        cardNumber?.let{
            viewModel.getCardInfo(it)
        }



        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { cardState ->
                    when (cardState) {
                        is CardInfoUiState.Success -> {
                            cardInfoActivityBinding.loadingGroup.visibility = View.GONE
                            loadUi(cardState.cardInfo)

                        }

                        is CardInfoUiState.Loading -> {
                            cardInfoActivityBinding.loadingGroup.visibility = View.VISIBLE
                            cardInfoActivityBinding.infoCard.visibility = View.GONE


                        }

                        is CardInfoUiState.Error -> {
                            cardInfoActivityBinding.loadingGroup.visibility = View.GONE
                            Toast.makeText(
                                this@CardInfoActivity,
                                "Invalid Card or response",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

        }
    }

    private fun loadUi(cardInfo: CardInfo) {
        cardInfoActivityBinding.infoCard.visibility = View.VISIBLE
        cardInfoActivityBinding.cardBrand.text = cardInfo.brand
        cardInfo.bank?.let { bank ->
            cardInfoActivityBinding.cardBank.text = bank.name
        }
        cardInfo.type?.let {
            cardInfoActivityBinding.cardType.text = it

        }
        cardInfo.country?.let { country ->
            cardInfoActivityBinding.country.text = country.name ?: ""

        }


    }
}