package com.sherry.nyttopstories.api

import com.sherry.nyttopstories.model.TopStoryResponse

interface TopStoriesRepository {
    suspend fun getAllTopStories(): ApiResult<TopStoryResponse, String>
}