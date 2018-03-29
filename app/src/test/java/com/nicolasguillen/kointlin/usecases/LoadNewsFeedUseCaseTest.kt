package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoadNewsFeedUseCaseTest {

    @Mock private lateinit var mockApiRepository: ApiRepository

    private lateinit var testee: LoadNewsFeedUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = LoadNewsFeedUseCaseImpl(mockApiRepository)
    }

    @Test
    fun test_fetchNewsFeed_when_isListOf5_then_emit5News(){
        //Arrange
        val test = TestObserver.create<FetchNewsFeedResult>()
        Mockito.doReturn(Single.just(listOf(
                CoindeskFeed("Enterprises Building Blockchain Confront Tech Limitations", "url", "url", "https://media.coindesk.com/uploads/2018/03/20180322_171743-copy-206x115.jpg", "Mar 23, 2018 at 11:40"),
                CoindeskFeed("Japan Warns Binance Exchange Over Licensing", "url", "url", "https://media.coindesk.com/uploads/2018/03/japanese-yen-201x134.jpg", "Mar 23, 2018 at 10:40"),
                CoindeskFeed("Blockchain Standardization Tops Chinese IT Ministry's 2018 Agenda", "url", "url", "https://media.coindesk.com/uploads/2018/03/china-flag-1-201x134.jpg", "Mar 23, 2018 at 10:00"),
                CoindeskFeed("Blockchain Remittances Face Efficiency Hurdle, Says Taiwan Central Bank", "url", "url", "https://media.coindesk.com/uploads/2018/03/Taiwan-dollar-1-180x134.jpg", "Mar 23, 2018 at 09:00"),
                CoindeskFeed("Coinbase In Talks to Buy Bitcoin Startup Earn.com", "url", "url", "https://media.coindesk.com/uploads/2018/03/Screen-Shot-2018-03-22-at-10.41.48-PM-206x134.png", "Mar 23, 2018 at 09:00")
        ))).whenever(mockApiRepository).getCoindeskNewsFeed()

        //Act
        testee.fetchNewsFeed().subscribe(test)

        //Assert
        assertTrue(test.events[0][0] is FetchNewsFeedResult.Success)
        val list = (test.events[0][0] as FetchNewsFeedResult.Success).newsFeedList
        assertTrue(list.size == 5)
    }

}