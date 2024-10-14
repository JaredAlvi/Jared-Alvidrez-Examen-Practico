package com.example.jaredalvidrezexamenpractico.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.jaredalvidrezexamenpractico.data.remote.FirebaseAuthService

class AuthViewModel(private val authService: FirebaseAuthService) : ViewModel() {

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authService.login(email, password, onSuccess, onFailure)
    }

    fun register(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authService.register(email, password, onSuccess, onFailure)
    }

    fun logout() {
        authService.logout()
    }

    fun getCurrentUser() = authService.getCurrentUser()
}