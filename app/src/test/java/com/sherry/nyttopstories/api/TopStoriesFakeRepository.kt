package com.sherry.nyttopstories.api

import com.sherry.nyttopstories.model.StoryResult
import com.sherry.nyttopstories.model.TopStoryResponse

/**
 * Fake Story repository to mock the actual server api call result.
 */
class TopStoriesFakeRepository: TopStoriesRepository {

    private var networkError = false
    private var resultFailure = false

    fun shouldReturnNetworkError(value: Boolean){
        networkError = value
    }

    fun shouldReturnResultFailure(result: Boolean){
        resultFailure = result
    }

    override suspend fun getAllTopStories(): ApiResult<TopStoryResponse, String> {
        return when {
            networkError -> {
                ApiResult.NetworkError
            }
            resultFailure -> {
                ApiResult.OnFailure("Error")
            }
            else -> {
                try {
                    ApiResult.OnSuccess(fakeResponse())
                }catch (e: Exception){
                    ApiResult.OnFailure("Error")
                }

            }
        }
    }

    private fun fakeResponse(): TopStoryResponse{
        val list = ArrayList<StoryResult>()
        list.add(fakeStoryResult())
        return TopStoryResponse("", "", 1, list,"","")
    }

    fun fakeStoryResult(): StoryResult = StoryResult("", "", "", ArrayList(),
        ArrayList(), "", "", "", ArrayList(), ArrayList(),
        ArrayList(), "", "", "", "", "", "", "","")
}