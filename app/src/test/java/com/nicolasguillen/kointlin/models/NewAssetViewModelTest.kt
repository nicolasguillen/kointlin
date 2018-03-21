package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.any
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import com.nicolasguillen.kointlin.usecases.LoadTopAssetsResult
import com.nicolasguillen.kointlin.usecases.NewAssetUseCase
import com.nicolasguillen.kointlin.usecases.StoreAssetResult
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class NewAssetViewModelTest {

    @Mock private lateinit var mockUseCase: NewAssetUseCase

    private lateinit var testee: NewAssetViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = NewAssetViewModel(mockUseCase)
    }

    @Test
    fun test_viewDidLoad_when_didFetchOneItem_then_emitOneCoin(){
        //Arrange
        val test = TestObserver.create<List<TopCoin>>()
        testee.outputs.allCoins().subscribe(test)
        doReturn(just(LoadTopAssetsResult.Success(listOf(
                TopCoin("BTC", "Bitcoin", "BTC", "100")
        )))).whenever(mockUseCase).loadAllTopCoins()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        assertTrue(test.events[0].size == 1)
    }

    @Test
    fun test_didPressSave_when_assetIsValidAndStoreDidSuccess_then_emitDidSave(){
        //Arrange
        val test = TestObserver.create<Unit>()
        testee.outputs.didSave().subscribe(test)
        doReturn(just(LoadTopAssetsResult.Success(listOf(
                TopCoin("BTC", "Bitcoin", "BTC", "100")
        )))).whenever(mockUseCase).loadAllTopCoins()
        doReturn(just(StoreAssetResult.Success))
                .whenever(mockUseCase).storeNewAsset(any())

        //Act
        testee.inputs.viewDidLoad()
        testee.inputs.didSelectCoin("Bitcoin")
        testee.inputs.didEnterAmount("1.0")
        testee.inputs.didPressSave()

        //Assert
        test.assertValueCount(1)
    }
}