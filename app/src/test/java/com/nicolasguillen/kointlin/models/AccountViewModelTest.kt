package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.usecases.AccountUseCase
import com.nicolasguillen.kointlin.usecases.GetPriceResult
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AccountViewModelTest {

    @Mock private lateinit var mockUseCase: AccountUseCase

    private lateinit var testee: AccountViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = AccountViewModel(mockUseCase)
    }

    @Test
    fun test_viewDidLoad_then_getDisplayableAssets(){
        //Arrange
        doReturn(just(GetPriceResult.Success(emptyList())))
                .whenever(mockUseCase).getDisplayableAssets()


        //Act
        testee.inputs.viewDidLoad()

        //Assert
        verify(mockUseCase).getDisplayableAssets()
    }

    @Test
    fun test_didPressAdd_then_startNewAssetActivity(){
        //Arrange
        val test = TestObserver.create<Unit>()
        testee.outputs.startNewAssetActivity().subscribe(test)

        //Act
        testee.inputs.didPressAdd()

        //Assert
        test.assertValueCount(1)
    }
}