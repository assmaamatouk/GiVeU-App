package com.example.giveu.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.giveu.domain.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String, val description: String, val imageUrl: String,
    val phoneNumber: String, val location: String, val category: String
)

fun ArticleEntity.toDomain() =
    Article(id, title, description, imageUrl, phoneNumber, location, category)

fun Article.toEntity() =
    ArticleEntity(id, title, description, imageUrl, phoneNumber, location, category)
