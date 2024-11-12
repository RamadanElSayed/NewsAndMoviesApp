package com.example.newsapp.presentabon.newsscreen

import com.example.newsapp.data.models.Article

sealed class NewsScreenEvent{
    data class OnNewsCardClicked(val article: Article) : NewsScreenEvent()
    data class OnCategoryChanged(val category: String) : NewsScreenEvent()
    data class OnSearchQueryChanged(val searchQuery: String) : NewsScreenEvent()
    data object OnSearchIconClicked :NewsScreenEvent()
    data object OnCloseIconClicked :NewsScreenEvent()

}
