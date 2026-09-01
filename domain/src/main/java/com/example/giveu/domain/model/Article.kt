package com.example.giveu.domain.model

import java.io.Serializable


data class Article(
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageUrl: String = "",
    val phoneNumber: String = "",
    val location: String = "",
    val category: String
) : Serializable
