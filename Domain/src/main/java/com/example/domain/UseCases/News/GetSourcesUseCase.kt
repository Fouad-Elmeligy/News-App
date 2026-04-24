package com.example.domain.UseCases.News

import com.example.domain.Repositories.News.NewsRepository
import javax.inject.Inject
class GetSourcesUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend fun invoke(categoryId: String) = repository.getSources(categoryId)
}