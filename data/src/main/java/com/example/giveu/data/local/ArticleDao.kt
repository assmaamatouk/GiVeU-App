package com.example.giveu.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Query("SELECT * FROM articles WHERE LOWER(category) IN (:categories) ORDER BY id DESC")
    fun byCategories(categories: List<String>): Flow<List<ArticleEntity>>
}
