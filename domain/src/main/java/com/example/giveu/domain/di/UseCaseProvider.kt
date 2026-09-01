package com.example.giveu.domain.di

import com.example.giveu.domain.usecases.FetchRemoteArticlesUseCase
import com.example.giveu.domain.usecases.FetchRemoteArticlesUseCaseImpl
import com.example.giveu.domain.usecases.GetLocalArticlesUseCase
import com.example.giveu.domain.usecases.GetLocalArticlesUseCaseImpl
import com.example.giveu.domain.usecases.SaveArticleUseCase
import com.example.giveu.domain.usecases.SaveArticleUseCaseImpl

object UseCaseProvider {

    lateinit var getLocalArticlesUseCase: GetLocalArticlesUseCase
    lateinit var fetchRemoteArticlesUseCase: FetchRemoteArticlesUseCase
    lateinit var saveArticleUseCase: SaveArticleUseCase

    fun setup(
        repositoryProvider: RepositoryProvider,
    ) {
        getLocalArticlesUseCase = GetLocalArticlesUseCaseImpl(
            articleRepository = repositoryProvider.articleRepository
        )

        fetchRemoteArticlesUseCase = FetchRemoteArticlesUseCaseImpl(
            articleRepository = repositoryProvider.articleRepository
        )

        saveArticleUseCase = SaveArticleUseCaseImpl(
            articleRepository = repositoryProvider.articleRepository
        )
    }
}
