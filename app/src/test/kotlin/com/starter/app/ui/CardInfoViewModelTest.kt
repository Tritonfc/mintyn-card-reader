package com.starter.app.ui

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.starter.app.CoroutineRule
import com.starter.app.data.fakeCardInfo
import com.starter.app.repository.CardRepository
import com.starter.app.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception


@RunWith(MockitoJUnitRunner::class)
class CardInfoViewModelTest{

    private val cardRepositoryMock:CardRepository = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = CoroutineRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `calling the getCardInfo function with a valid card returns Success with card info`()= runTest{
        //Arrange
        val fakeCardInfoStub = Result.Success(fakeCardInfo)
        val viewModel = CardInfoViewModel(cardRepositoryMock)

        whenever(cardRepositoryMock.getCardInfo("00000000")).doReturn(fakeCardInfoStub)
        val expectedValue = CardInfoUiState.Success(fakeCardInfo)


        //Act
         viewModel.getCardInfo("00000000")
        advanceUntilIdle()

        val actualValue = viewModel.uiState.value

        //Assert
        assertEquals(expectedValue,actualValue)


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `calling getCardInfo with invalid card  returns a UI state of error`()= runTest{

        val fakeCardInfoStub = Result.Error(error = Exception())
        val viewModel = CardInfoViewModel(cardRepositoryMock)

        whenever(cardRepositoryMock.getCardInfo("000AA")).doReturn(fakeCardInfoStub)

        viewModel.getCardInfo("000AA")
        advanceUntilIdle()

        val actualValue = viewModel.uiState.value

        assert(actualValue is CardInfoUiState.Error)

    }

}