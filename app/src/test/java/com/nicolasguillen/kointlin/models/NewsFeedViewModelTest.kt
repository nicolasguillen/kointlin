package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import com.nicolasguillen.kointlin.usecases.*
import com.nicolasguillen.kointlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsFeedViewModelTest {

    @Mock private lateinit var mockUseCase: LoadNewsFeedUseCase

    private lateinit var testee: NewsFeedViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        testee = NewsFeedViewModel(mockUseCase)
    }

    @Test
    fun test_viewDidLoad_when_didFetchOneFeed_then_emitOneFeed(){
        //Arrange
        val test = TestObserver.create<List<DisplayableFeed>>()
        testee.outputs.newsFeed().subscribe(test)
        Mockito.doReturn(Single.just(FetchNewsFeedResult.Success(listOf(DisplayableFeed(CoindeskFeed("Title", "url", "shareUrl", "image", "date"))))))
                .whenever(mockUseCase).fetchNewsFeed()

        //Act
        testee.inputs.viewDidLoad()

        //Assert
        assertTrue(test.events[0].size == 1)
    }

}