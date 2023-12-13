package sam.sultan.newsapp.models.entities

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
