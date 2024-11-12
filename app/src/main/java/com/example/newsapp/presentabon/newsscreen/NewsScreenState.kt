package com.example.newsapp.presentabon.newsscreen

import com.example.newsapp.data.models.Article

data class NewsScreenState(
    val isLoading:Boolean=false,
    val articles:List<Article> = emptyList(),
    val error:String?=null,
    val isSearchBarVisible:Boolean=false,
    val selectedArticle: Article?=null,
    val category:String="General",
    val searchQuery:String = ""
)
