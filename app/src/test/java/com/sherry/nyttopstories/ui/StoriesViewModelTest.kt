package com.sherry.nyttopstories.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sherry.nyttopstories.api.ApiResult
import com.sherry.nyttopstories.base.BaseViewModel
import com.sherry.nyttopstories.LiveDataTestUtil
import com.sherry.nyttopstories.MyCoroutineRule
import com.sherry.nyttopstories.api.TopStoriesFakeRepository
import com.sherry.nyttopstories.model.StoryResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * To Test StoriesViewModel functionality.
 */
@ExperimentalCoroutinesApi
class StoriesViewModelTest: BaseViewModel() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MyCoroutineRule()

    private lateinit var viewModel: StoriesViewModel

    private lateinit var repository: TopStoriesFakeRepository

    @Before
    fun setUp(){
        repository = TopStoriesFakeRepository()
        viewModel = StoriesViewModel(repository)
    }

    @Test
    fun `check if loader was dismissed`(){
        with(viewModel){
            coroutineRule.runBlockingTest {
                fetchData()
            }
            val loader = LiveDataTestUtil.getValue(getIsLoadingLiveData())
            assertNotNull(loader)
            assertEquals(false, getIsLoadingLiveData().value)
        }
    }

    @Test
    fun `network Not available`(){
        with(viewModel){
            repository.shouldReturnNetworkError(true)
            coroutineRule.runBlockingTest {
                fetchData()
            }
            val networkNotAvailable = LiveDataTestUtil.getValue(networkError())
            assertNotNull(networkNotAvailable)
            assertEquals(true, networkNotAvailable)
        }
    }

    @Test
    fun `check if response returned from server is failure`(){
        with(viewModel){
            var resultFailure = ""
            repository.shouldReturnResultFailure(true)
            coroutineRule.runBlockingTest {
                resultFailure = when(val result = repository.getAllTopStories()){
                    is ApiResult.OnFailure -> {
                        result.exception
                    }
                    else -> {
                        ""
                    }
                }
                fetchData()
            }
            val displayError = LiveDataTestUtil.getValue(getDisplayErrorLiveData())
            assertNotNull(displayError)
            assertEquals(resultFailure, displayError)
        }
    }

    @Test
    fun `check if response returned from server is success`(){
        with(viewModel){
            var storiesList: ArrayList<StoryResult>? = ArrayList()
            coroutineRule.runBlockingTest {
                storiesList = when(val result = repository.getAllTopStories()){
                    is ApiResult.OnSuccess ->{
                        result.response.results as ArrayList<StoryResult>
                    }
                    else -> {
                        null
                    }
                }
                fetchData()
            }
            val fetchedData = LiveDataTestUtil.getValue(storiesMutableList)
            assertNotNull(fetchedData)
            assertEquals(storiesList, fetchedData)
        }
    }

    @Test
    fun `check if details has data`(){
        with(viewModel){
            coroutineRule.runBlockingTest {
                fetchData()
            }
            val expected = repository.fakeStoryResult()
            setStoryDetailsData(expected)
            val result = LiveDataTestUtil.getValue(storyDetailsData)
            assertEquals(expected, result)
            webViewLink = result?.url
            assertEquals(expected.url, webViewLink)
        }
    }
}