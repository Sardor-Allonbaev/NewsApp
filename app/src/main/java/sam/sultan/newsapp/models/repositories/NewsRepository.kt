package sam.sultan.newsapp.models.repositories

import android.content.Context
import sam.sultan.newsapp.models.api.RetrofitInstance
import sam.sultan.newsapp.models.database.NewsDao
import sam.sultan.newsapp.models.database.NewsDataBase
import sam.sultan.newsapp.models.entities.Article

class NewsRepository(private val dao: NewsDao) {

    suspend fun getAllNews() = RetrofitInstance.api.getAllNews()

    //Database methods
    suspend fun save(article: Article) = dao.save(article)

    fun getNewsFromDb() = dao.getAll()

    suspend fun delete(article: Article) = dao.delete(article)

}