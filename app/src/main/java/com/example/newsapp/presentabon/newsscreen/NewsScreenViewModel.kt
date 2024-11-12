package com.example.newsapp.presentabon.newsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.GetTopHeadlineUseCase
import com.example.newsapp.domain.usecase.SearchForNewsUseCase
import com.example.newsapp.util.Resource
import com.example.newsapp.data.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val getTopHeadlineUseCase: GetTopHeadlineUseCase,
    private val searchForNewsUseCase: SearchForNewsUseCase
) : ViewModel() {

    var state by mutableStateOf(NewsScreenState())
    private var searchJob: Job? = null
    private val searchDelayMillis = 1000L

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.OnCategoryChanged -> updateCategory(event.category)
            is NewsScreenEvent.OnNewsCardClicked -> selectArticle(event.article)
            NewsScreenEvent.OnSearchIconClicked -> showSearchBar()
            NewsScreenEvent.OnCloseIconClicked -> closeSearchBar()
            is NewsScreenEvent.OnSearchQueryChanged -> updateSearchQuery(event.searchQuery)
        }
    }

    private fun updateCategory(category: String) {
        state = state.copy(category = category)
        fetchNewsArticles(category)
    }

    private fun selectArticle(article: Article) {
        state = state.copy(selectedArticle = article)
    }

    private fun showSearchBar() {
        state = state.copy(isSearchBarVisible = true, articles = emptyList())
    }

    private fun closeSearchBar() {
        state = state.copy(isSearchBarVisible = false)
        fetchNewsArticles(category = state.category)
    }

    private fun updateSearchQuery(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        if (query.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(searchDelayMillis)
                performSearch(query)
            }
        }
    }

    private fun fetchNewsArticles(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            when (val result = getTopHeadlineUseCase(category)) {
                is Resource.Success -> {
                    state = state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,
                        isLoading = false,
                        articles = emptyList()
                    )
                }
            }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            when (val result = searchForNewsUseCase(query)) {
                is Resource.Success -> {
                    state = state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,
                        isLoading = false,
                        articles = emptyList()
                    )
                }
            }
        }
    }
}
