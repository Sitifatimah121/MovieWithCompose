package com.example.moviecompose.main

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.moviecompose.model.UserModel
import com.example.moviecompose.model.UserPreferences
import com.example.moviecompose.remote.ApiConfig
import com.example.moviecompose.model.Movie
import com.example.moviecompose.remote.MovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreferences) : ViewModel() {
//    private var _movie = MutableLiveData<MovieResponse>()
//    val movie get() = _movie

    val movie : MutableState<List<Movie>> = mutableStateOf(listOf())


    fun getAllMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "getAllMovie response: eksekusi get allmovie")
            ApiConfig.getApiService().getMovie().let {
//                movie.postValue(it.body())
                Log.d(TAG, "getAllMovie response: ${it}")
                if (it.isSuccessful){
                    movie.value = it.body()?.results!!
                    Log.d(TAG, "getAllMovie data: ${it}")
                }
            }
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}