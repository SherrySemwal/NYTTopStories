package com.sherry.nyttopstories.api

import com.sherry.nyttopstories.base.BaseDataSource
import com.sherry.nyttopstories.model.TopStoryResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class ImpTopStoriesRepository @Inject constructor(private val service: ApiInterface)
    : BaseDataSource(), TopStoriesRepository {
    private val apiKEY = "jCCOXP24uyjG8USdjJaObWbCNJnmOUu0"

    override suspend fun getAllTopStories(): ApiResult<TopStoryResponse, String> =
        coroutineScope { getResult { service.getAllTopStories(apiKEY) } }
}