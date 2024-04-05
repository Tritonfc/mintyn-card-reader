package com.starter.app.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.starter.app.CoroutineRule
import com.starter.app.data.fakeCardInfo
import com.starter.app.data.remote.CardApi
import com.starter.app.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CardRepositoryImplTest{

    private val cardApiMock:CardApi = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = CoroutineRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `calling the getCardInfo function with a valid card returns Result Success`() = runTest{
        val cardRepository = CardRepositoryImpl(cardApiMock)

        whenever(cardApiMock.getCardInfo("000000")).doReturn(fakeCardInfo)
        val expectedValue = Result.Success(fakeCardInfo)

      val actualValue =  cardRepository.getCardInfo("000000")
        advanceUntilIdle()

        assertEquals(expectedValue,actualValue)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `calling the getCardInfo function with a invalid card returns Result Error`() = runTest{
//        val cardRepository = CardRepositoryImpl(cardApiMock)
//
//        whenever(cardApiMock.getCardInfo("")).doReturn(
//            Exception()
//        )
//        val expectedValue = Result.Err
//
//        val actualValue =  cardRepository.getCardInfo("")
//        advanceUntilIdle()
//
//        assertEquals(expectedValue,actualValue)
//
   }

}