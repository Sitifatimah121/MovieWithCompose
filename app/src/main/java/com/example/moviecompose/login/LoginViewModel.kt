package com.example.moviecompose.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.UserModel
import com.example.moviecompose.model.UserPreferences
import kotlinx.coroutines.launch

class LoginViewModel (private val pref: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(email: String, password: String) : Boolean {
        viewModelScope.launch {
            pref.login()
            Log.d(TAG, "login: ${pref.getUser().asLiveData().value?.email}")
        }

        return pref.getUser().asLiveData().value?.isLogin ?: false
    }
}