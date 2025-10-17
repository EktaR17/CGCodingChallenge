package com.example.userinformation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.User
import com.example.network.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface UsersUiState {
    object Loading : UsersUiState
    data class Success(val users: List<User>) : UsersUiState
    data class Error(val message: String) : UsersUiState
}

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState

    init {
        loadUsers()
    }

    /**
     * Public function to retry / reload
     */
    fun loadUsers() {
        _uiState.value = UsersUiState.Loading

        viewModelScope.launch {
            try {
                val users = repository.getUsers()
                _uiState.value = UsersUiState.Success(users)
            } catch (e: IOException) {
                // network or IO error
                _uiState.value = UsersUiState.Error(e.localizedMessage ?: "Network error")
            } catch (e: Exception) {
                // generic error
                _uiState.value = UsersUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}