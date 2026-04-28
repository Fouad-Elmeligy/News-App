package com.example.domain.UseCases.News

import com.example.domain.Repositories.News.NewsRepository
class GetSourcesUseCase(private val repository: NewsRepository) {
    suspend fun invoke(categoryId: String) = repository.getSources(categoryId)
}