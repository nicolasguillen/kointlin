package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.any
import com.nicolasguillen.kointlin.usecases.DisplayableCurrency
import com.nicolasguillen.kointlin.usecases.FetchAvailableCurrenciesResult
import com.nicolasguillen.kointlin.usecases.SaveCurrencyResult
import com.nicolasguillen.kointlin.usecases.SetCurrencyUseCase
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class SetCurrencyViewModelTest {

    @Mock private lateinit var mockUseCase: SetCurrencyUseCase

    private lateinit var testee: SetCurrencyViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = SetCurrencyViewModel(mockUseCase)
    }

    @Test
    fun test_viewDidLoad_when_didFetchOneItem_then_emitOneCurrency(){
        //Arrange
        val test = TestObserver.create<List<DisplayableCurrency>>()
        testee.outputs.currencies().subscribe(test)
        doReturn(just(FetchAvailableCurrenciesResult.Success(listOf(DisplayableCurrency("USD", "Dollar")))))
                .whenever(mockUseCase).fetchAvailableCurrencies()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        assertTrue(test.events[0].size == 1)
    }

    @Test
    fun test_didPressSave_when_saveCurrencySuccess_then_emitDidSaveSettings(){
        //Arrange
        val test = TestObserver.create<Unit>()
        testee.outputs.didSaveSettings().subscribe(test)
        doReturn(just(FetchAvailableCurrenciesResult.Success(listOf(DisplayableCurrency("USD", "Dollar")))))
                .whenever(mockUseCase).fetchAvailableCurrencies()
        doReturn(just(SaveCurrencyResult.Success))
                .whenever(mockUseCase).saveCurrency(any())

        //Act
        testee.inputs.viewDidLoad()
        testee.inputs.didSelectCurrency(DisplayableCurrency("USD", "Dollar"))

        //Assert
        test.assertValueCount(1)
    }
}