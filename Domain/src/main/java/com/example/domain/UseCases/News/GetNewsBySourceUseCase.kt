package com.example.domain.UseCases.News

import com.example.domain.Repositories.News.NewsRepository
import javax.inject.Inject
class GetNewsBySourceUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(sourceId: String) = repository.getNewsBySource(sourceId)
}