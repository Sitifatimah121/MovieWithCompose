package com.example.moviecompose.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.moviecompose.model.Movie
import com.example.moviecompose.remote.ApiServices
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    val errorMessage = MutableLiveData<String>()
    //    val genre = MutableLiveData<GenreResponse>()
    val movie : MutableState<List<Movie>> = mutableStateOf(listOf())
    var job: Job? = null
    val isLoading : MutableState<Boolean> = mutableStateOf(true)
    private val apiService: ApiServices

    init {
        val retrofit =  Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiServices::class.java)
    }

    val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun fetchMovie(idGenre: String = ""){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getMovie()
            if (response.isSuccessful){
                movie.value = response.body()!!.results
                isLoading.value = false
            }else{
                onError("Error: ${response.message()}")
            }
        }
    }

    fun onError(message: String){
        errorMessage.postValue(message)
        isLoading.value = false
    }

    fun jobCLeared(){
        job?.cancel()
    }
}