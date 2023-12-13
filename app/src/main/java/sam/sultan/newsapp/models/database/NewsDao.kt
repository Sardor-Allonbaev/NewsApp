package sam.sultan.newsapp.models.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import sam.sultan.newsapp.models.entities.Article

@Dao
interface NewsDao {

    @Insert
    suspend fun save(article: Article)

    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)

}