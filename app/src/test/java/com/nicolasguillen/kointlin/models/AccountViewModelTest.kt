package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.any
import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.CoinPage
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Flowable.just
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AccountViewModelTest {

    @Mock private lateinit var mockApiRepository: ApiRepository
    @Mock private lateinit var mockWalletRepository: WalletRepository

    private lateinit var testee: AccountViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = AccountViewModel(mockApiRepository, mockWalletRepository)
    }

    @Test
    fun test_viewDidLoad_then_getAllAssets(){
        //Arrange
        doReturn(just(listOf<Asset>()))
                .whenever(mockWalletRepository).getAllAssets()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        verify(mockWalletRepository).getAllAssets()
    }

    @Test
    fun test_didPressAdd_then_startNewAssetActivity(){
        //Arrange
        val test = TestSubscriber.create<Unit>()
        testee.outputs.startNewAssetActivity().subscribe(test)

        //Act
        testee.inputs.didPressAdd()

        //Assert
        test.assertValueCount(1)
    }

    @Test
    fun test_viewDidLoad_when_walletEmpty_then_totalAmountIs0(){
        //Arrange
        val test = TestSubscriber.create<Double>()
        testee.outputs.totalAmount().subscribe(test)
        doReturn(just(listOf<Asset>()))
                .whenever(mockWalletRepository).getAllAssets()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        test.assertValue(0.0)
    }

    @Test
    fun test_viewDidLoad_when_walletHasBTCAndPriceIs100_then_totalAmountIs100(){
        //Arrange
        val test = TestSubscriber.create<Double>()
        testee.outputs.totalAmount().subscribe(test)
        doReturn(just(listOf(
                Asset("BTC", "Bitcoin", 1.0)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(just(CoinPage("BTC", 100.0))).whenever(mockApiRepository).getPageFromCoin(any())

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        test.assertValue(100.0)
    }

    @Test
    fun test_viewDidLoad_when_walletHasBTCAndETHAndPriceIs100And2_then_totalAmountIs101(){
        //Arrange
        val test = TestSubscriber.create<Double>()
        testee.outputs.totalAmount().subscribe(test)
        doReturn(just(listOf(
                Asset("BTC", "Bitcoin", 1.0),
                Asset("ETH", "Etherium", 0.5)
        ))).whenever(mockWalletRepository).getAllAssets()
        doReturn(just(CoinPage("BTC", 100.0))).whenever(mockApiRepository).getPageFromCoin("BTC")
        doReturn(just(CoinPage("ETH", 2.0))).whenever(mockApiRepository).getPageFromCoin("ETH")

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        test.assertValue(101.0)
    }

}