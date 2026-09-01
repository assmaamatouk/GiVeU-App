package com.example.giveu.data.repository

import android.content.Context
import com.example.giveu.data.local.AppDatabase
import com.example.giveu.data.local.toDomain
import com.example.giveu.data.local.toEntity
import com.example.giveu.data.remote.RemoteServices
import com.example.giveu.domain.model.Article
import com.example.giveu.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(context: Context) : ArticleRepository {
    private val dao = AppDatabase.get(context).articleDao()
    override fun localArticles(categories: List<String>): Flow<List<Article>> =
        dao.byCategories(categories.map { it.lowercase() })
            .map { list -> list.map { it.toDomain() } }

    override suspend fun save(article: Article) = dao.insert(article.toEntity())
    override suspend fun remoteArticles(categories: List<String>): List<Article> =
        categories.flatMap { category ->
            RemoteServices.dummy.products(category).products.map { product ->
                val contact = demoContact(product.id)
                Article(
                    product.id,
                    product.title,
                    italianDescription(product.category),
                    product.thumbnail,
                    contact.first,
                    contact.second,
                    product.category
                )
            }
        }

    override suspend fun books(): List<Article> = try {
        RemoteServices.books.books("fiction").works.mapIndexed { index, book ->
            val cover = book.cover_id?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" } ?: ""
            val contact = demoContact(index)
            val description =
                if (book.description?.isJsonPrimitive == true) book.description.asString else "Libro disponibile per la donazione"
            Article(
                index + 100000,
                book.title,
                description,
                cover,
                contact.first,
                contact.second,
                "books"
            )
        }
    } catch (_: Exception) {

        listOf("Il piccolo principe", "Orgoglio e pregiudizio", "1984").mapIndexed { index, title ->
            val contact = demoContact(index)
            Article(
                index + 200000,
                title,
                "Libro di esempio: controlla la disponibilità contattando il donatore.",
                "",
                contact.first,
                contact.second,
                "books"
            )
        }
    }

    // Dati dimostrativi per rendere testabile le funzioni Chiama e Maps.
    private fun demoContact(id: Int): Pair<String, String> {
        val contacts = listOf(
            "+39 333 1234567" to "Piazza Maggiore, Bologna",
            "+39 347 7654321" to "Via Indipendenza 12, Bologna",
            "+39 320 2468101" to "Piazza San Babila, Milano",
            "+39 339 1357913" to "Via del Corso 45, Roma",
            "+39 351 9876543" to "Piazza della Signoria, Firenze"
        )
        return contacts[id.mod(contacts.size)]
    }

    private fun italianDescription(category: String): String = when (category) {
        "laptops", "smartphones" -> "Dispositivo usato ma funzionante, disponibile per il riuso gratuito."
        "mens-shirts", "womens-dresses" -> "Capo di abbigliamento in buone condizioni, pronto per una seconda vita."
        "home-decoration", "furniture" -> "Oggetto per la casa disponibile gratuitamente per il riuso."
        "beauty", "skincare" -> "Prodotto disponibile per la donazione; contatta il donatore per i dettagli."
        "automotive", "motorcycle" -> "Accessorio disponibile gratuitamente; verifica i dettagli con il donatore."
        else -> "Oggetto disponibile gratuitamente per il riuso e la solidarietà."
    }
}
