package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import com.nicolasguillen.kointlin.storage.AppSettingsRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.AppSettings
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
    @Mock private lateinit var mockAppSettingsRepository: AppSettingsRepository

    private lateinit var testee: AccountUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = AccountUseCaseImpl(mockApiRepository, mockWalletRepository, mockAppSettingsRepository)
    }

    @Test
    fun test_getDisplayableAssets_when_isEmptyWallet_then_emitPriceZero(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(listOf<Asset>()))
                .whenever(mockWalletRepository).getAllAssets()
        doReturn(just(AppSettings("1", "USD")))
                .whenever(mockAppSettingsRepository).getAppSettings()

        //Act
        testee.getDisplayableAssets().subscribe(test)

        //Assert
        assertTrue(test.events[0][0] is GetPriceResult.Success)
        val list = (test.events[0][0] as GetPriceResult.Success).list
        assertTrue(list.isEmpty())
    }

    @Test
    fun test_getDisplayableAssets_when_ownBTCAndPriceIs100_then_totalAmountIs100(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(AppSettings("1", "USD")))
                .whenever(mockAppSettingsRepository).getAppSettings()
        doReturn(just(listOf(
                Asset("BTC", "BTC", "Bitcoin", 1.0)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(Single.just(listOf(TopCoin("BTC", "Bitcoin", "BTC", "100.0", "0"))))
                .whenever(mockApiRepository).getCoinFromId("BTC", "USD")

        //Act
        testee.getDisplayableAssets().subscribe(test)

        //Assert
        val list = (test.events[0][0] as GetPriceResult.Success).list
        assertTrue(list.sumByDouble { it.currentPrice } == 100.0)
    }

    @Test
    fun test_getDisplayableAssets_when_ownBTCAndETHAndPriceIs100And2_then_totalAmountIs102(){
        //Arrange
        val test = TestObserver.create<GetPriceResult>()
        doReturn(just(AppSettings("1", "USD")))
                .whenever(mockAppSettingsRepository).getAppSettings()
        doReturn(just(listOf(
                Asset("BTC", "BTC", "Bitcoin", 1.0),
                Asset("ETH", "ETH", "Ethereum", 1.0)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(just(listOf(TopCoin("BTC", "Bitcoin", "BTC", "100.0", "0"))))
                .whenever(mockApiRepository).getCoinFromId("BTC", "USD")
        doReturn(just(listOf(TopCoin("ETH", "Ethereum", "ETH", "2.0", "0"))))
                .whenever(mockApiRepository).getCoinFromId("ETH", "USD")

        //Act
        testee.getDisplayableAssets().subscribe(test)

        //Assert
        val list = (test.events[0][0] as GetPriceResult.Success).list
        assertTrue(list.sumByDouble { it.currentPrice } == 102.0)
    }
}