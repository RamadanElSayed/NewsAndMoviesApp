package com.example.newsapp.domain.usecase

import com.example.newsapp.data.models.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Resource
import javax.inject.Inject

class GetTopHeadlineUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(category: String): Resource<List<Article>> {
        return repository.getTopHeadline(category)
    }
}
