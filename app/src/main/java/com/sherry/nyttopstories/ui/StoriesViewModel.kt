package com.sherry.nyttopstories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sherry.nyttopstories.api.ApiResult
import com.sherry.nyttopstories.api.TopStoriesRepository
import com.sherry.nyttopstories.base.BaseViewModel
import com.sherry.nyttopstories.model.StoryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * [StoriesViewModel] View Model to share data between Story list and Story details screen
 * */
@HiltViewModel
class StoriesViewModel @Inject constructor(private val repository: TopStoriesRepository) :
    BaseViewModel() {

    private val _storiesMutableLiveData: MutableLiveData<List<StoryResult>> =
        MutableLiveData()
    val storiesMutableList: LiveData<List<StoryResult>> = _storiesMutableLiveData

    private val _storyDetailsLiveData = MutableLiveData<StoryResult>()
    val storyDetailsData: LiveData<StoryResult> = _storyDetailsLiveData

    //To store web link to open a Review which shows more details of story
    var webViewLink: String? = null

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        isLoading.postValue(true)
        when (val result = repository.getAllTopStories()) {
            is ApiResult.NetworkError -> {
                stopLoader()
                networkError.postValue(true)
            }
            is ApiResult.OnSuccess -> {
                stopLoader()
                _storiesMutableLiveData.postValue(result.response.results)
            }
            is ApiResult.OnFailure -> {
                stopLoader()
                displayError.postValue(result.exception)
            }
        }
    }

    fun setStoryDetailsData(item: StoryResult) {
        _storyDetailsLiveData.value = item
    }

    private fun stopLoader() = isLoading.postValue(false)
}