package com.example.giveu.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.giveu.data.di.RepositoryProviderImpl

class ArticleRefreshWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = try {
        RepositoryProviderImpl(applicationContext).articleRepository.remoteArticles(listOf("home-decoration"))
        Result.success()
    } catch (_: Exception) {
        Result.retry()
    }
}
