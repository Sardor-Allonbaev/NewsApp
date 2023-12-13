package sam.sultan.newsapp.models.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sam.sultan.newsapp.models.entities.Article
import sam.sultan.newsapp.models.entities.News
import sam.sultan.newsapp.models.repositories.NewsRepository
import sam.sultan.newsapp.utils.Resource

class NewsViewModel(val repository: NewsRepository): ViewModel(){

    val news: MutableLiveData<Resource<News>> = MutableLiveData()

    val archivedNews = repository.getNewsFromDb()

    fun getAllNews(){
        viewModelScope.launch {
            news.postValue(Resource.Loading())
            val response = repository.getAllNews()
            if(response.isSuccessful){
                response.body().let {
                    news.postValue(Resource.Success(it!!))
                }
            }else{
                news.postValue(Resource.Error(response.message()))
            }
        }
    }

    fun saveArticle(article: Article){
        viewModelScope.launch {
            repository.save(article)
        }
    }

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            repository.delete(article)
        }
    }

}