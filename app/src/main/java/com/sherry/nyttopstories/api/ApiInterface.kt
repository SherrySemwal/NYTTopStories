package com.sherry.nyttopstories.api

import com.sherry.nyttopstories.model.TopStoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/home.json")
    suspend fun getAllTopStories(@Query("api-key") key: String)
    : Response<TopStoryResponse>
}