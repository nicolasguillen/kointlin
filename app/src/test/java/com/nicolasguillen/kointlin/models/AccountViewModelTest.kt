package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.storage.WalletRepository
import com.nicolasguillen.kointlin.storage.entities.Asset
import io.reactivex.Flowable.just
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AccountViewModelTest {

    @Mock lateinit var mockApiRepository: ApiRepository
    @Mock lateinit var mockWalletRepository: WalletRepository

    private lateinit var testee: AccountViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = AccountViewModel(mockApiRepository, mockWalletRepository)
    }

    @Test
    fun test1(){
        //Arrange
        doReturn(just(listOf(Asset("", "", 0.0))))
                .`when`(mockWalletRepository).getAllAssets()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        verify(mockWalletRepository).getAllAssets()
    }

}