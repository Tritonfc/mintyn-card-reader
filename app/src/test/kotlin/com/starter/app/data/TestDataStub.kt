package com.starter.app.data

import com.starter.app.data.models.Bank
import com.starter.app.data.models.CardInfo
import com.starter.app.data.models.Country
import com.starter.app.data.models.Number

val fakeCardInfo = CardInfo(
    Bank(
        city = "Hjørring",
        name = "Jyske Bank",
        phone = "+4589893300",
        url =  "www.jyskebank.dk",
    ),
    brand = "Visa/Dankort",
    country = Country(
        alpha2 =  "DK",
        currency = "DKK",
        emoji = "🇩🇰",
        latitude = 56,
        longitude = 10,
        name = "Denmark",
        numeric = "208"
    ),
    com.starter.app.data.models.Number(length =  16, luhn = true),
    prepaid =  false,
    scheme = "visa",
    type = "debit"
)

val fakeCardResponse = """
    {
      "number": {
        "length": 16,
        "luhn": true
      },
      "scheme": "visa",
      "type": "debit",
      "brand": "Visa/Dankort",
      "prepaid": false,
      "country": {
        "numeric": "208",
        "alpha2": "DK",
        "name": "Denmark",
        "emoji": "🇩🇰",
        "currency": "DKK",
        "latitude": 56,
        "longitude": 10
      },
      "bank": {
        "name": "Jyske Bank",
        "url": "www.jyskebank.dk",
        "phone": "+4589893300",
        "city": "Hjørring"
      }
    }
""".trimIndent()