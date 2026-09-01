package com.example.giveu.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.google.gson.JsonElement

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val category: String
)

data class ProductsResponse(val products: List<Product>)
data class BookWork(val title: String, val cover_id: Int?, val description: JsonElement?)
data class BooksResponse(val works: List<BookWork>)

interface DummyJsonService {
    @GET("products/category/{category}")
    suspend fun products(@Path("category") category: String): ProductsResponse
}

interface OpenLibraryService {
    @GET("subjects/{subject}.json?limit=20")
    suspend fun books(@Path("subject") subject: String): BooksResponse
}

object RemoteServices {
    val dummy: DummyJsonService by lazy {
        Retrofit.Builder().baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DummyJsonService::class.java)
    }
    val books: OpenLibraryService by lazy {
        Retrofit.Builder().baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(OpenLibraryService::class.java)
    }
}
