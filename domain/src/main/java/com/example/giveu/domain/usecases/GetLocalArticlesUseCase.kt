package com.example.giveu.domain.usecases

import com.example.giveu.domain.model.Article
import com.example.giveu.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

interface GetLocalArticlesUseCase {
    fun invoke(categories: List<String>): Flow<List<Article>>
}

class GetLocalArticlesUseCaseImpl(
    private val articleRepository: ArticleRepository
) : GetLocalArticlesUseCase {
    override fun invoke(categories: List<String>): Flow<List<Article>> {
        return articleRepository.localArticles(categories = categories)
    }
}
