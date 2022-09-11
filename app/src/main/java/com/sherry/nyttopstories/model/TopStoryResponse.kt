package com.sherry.nyttopstories.model

data class TopStoryResponse(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<StoryResult>,
    val section: String,
    val status: String
)