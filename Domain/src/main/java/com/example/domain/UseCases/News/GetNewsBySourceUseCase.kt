package com.example.domain.UseCases.News

import com.example.domain.Repositories.News.NewsRepository
class GetNewsBySourceUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(sourceId: String) = repository.getNewsBySource(sourceId)
}