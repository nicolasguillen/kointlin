package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.any
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class AccountUseCaseTest {

    @Mock private lateinit var mockApiRepository: ApiRepository
    @Mock private lateinit var mockWalletRepository: WalletRepository

    private lateinit var testee: AccountUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = AccountUseCaseImpl(mockApiRepository, mockWalletRepository)
    }

    @Test
    fun test_fetchAllMyAssets_when_didFetchMyAssets_then_emitSuccess(){
        //Arrange
        val test = TestObserver.create<FetchMyAssetsResult>()
        doReturn(just(listOf<Asset>()))
                .whenever(mockWalletRepository).getAllAssets()

        //Act
        testee.fetchAllMyAssets().subscribe(test)

        //Assert
        assertTrue(test.events[0][0] is FetchMyAssetsResult.Success)
    }

    @Test
    fun test_getPriceFromAllMyAssets_when_isEmptyWallet_then_emitPriceZero(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(listOf<Asset>()))
                .whenever(mockWalletRepository).getAllAssets()

        //Act
        testee.getPriceFromAllMyAssets().subscribe(test)

        //Assert
        assertTrue(test.events[0][0] is GetPriceResult.Success)
        assertTrue((test.events[0][0] as GetPriceResult.Success).price == 0.0)
    }

    @Test
    fun test_getPriceFromAllMyAssets_when_ownBTCAndPriceIs100_then_totalAmountIs100(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(listOf(
                Asset("BTC", "BTC", "Bitcoin", 1.0)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(Single.just(listOf(TopCoin("BTC", "Bitcoin", "BTC", "100.0"))))
                .whenever(mockApiRepository).getCoinFromId(any())

        //Act
        testee.getPriceFromAllMyAssets().subscribe(test)

        //Assert
        assertTrue((test.events[0][0] as GetPriceResult.Success).price == 100.0)
    }

    @Test
    fun test_getPriceFromAllMyAssets_when_ownBTCAndETHAndPriceIs100And2_then_totalAmountIs102(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(listOf(
                Asset("BTC", "BTC", "Bitcoin", 1.0),
                Asset("ETH", "ETH", "Etherium", 1.0)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(just(listOf(TopCoin("BTC", "Bitcoin", "BTC", "100.0"))))
                .whenever(mockApiRepository).getCoinFromId("BTC")
        doReturn(just(listOf(TopCoin("ETH", "Etherium", "ETH", "2.0"))))
                .whenever(mockApiRepository).getCoinFromId("ETH")

        //Act
        testee.getPriceFromAllMyAssets().subscribe(test)

        //Assert
        assertTrue((test.events[0][0] as GetPriceResult.Success).price == 102.0)
    }
}