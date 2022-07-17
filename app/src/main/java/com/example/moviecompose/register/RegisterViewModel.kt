package com.example.moviecompose.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.UserModel
import com.example.moviecompose.model.UserPreferences
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreferences) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}