package com.example.newsapp.domain.usecase

import com.example.newsapp.data.models.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Resource2
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlineUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(category: String): Flow<Resource2<List<Article>>>? {
        return repository.getTopHeadline(category)
    }
}
