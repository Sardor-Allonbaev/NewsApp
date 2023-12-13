package sam.sultan.newsapp.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var author: String? = null,
    var content: String? = null,
    var description: String? = null,
    var publishedAt: String? = null,
    var title: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
) : Serializable
