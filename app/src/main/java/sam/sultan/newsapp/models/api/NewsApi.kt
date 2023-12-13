package sam.sultan.newsapp.models.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sam.sultan.newsapp.models.entities.News
import sam.sultan.newsapp.utils.Constants.API_KEY

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getAllNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<News>

    @GET("/v2/everything")
    suspend fun searchNews(@Query("apiKey") apiKey: String = API_KEY): Call<News>

}