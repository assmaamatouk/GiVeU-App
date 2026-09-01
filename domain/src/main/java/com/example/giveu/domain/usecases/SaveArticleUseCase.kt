package com.example.giveu.domain.usecases

import com.example.giveu.domain.model.Article
import com.example.giveu.domain.repository.ArticleRepository

interface SaveArticleUseCase {
    suspend fun invoke(article: Article)
}

class SaveArticleUseCaseImpl(
    private val articleRepository: ArticleRepository
) : SaveArticleUseCase {
    override suspend fun invoke(article: Article) {
        articleRepository.save(article = article)
    }
}
