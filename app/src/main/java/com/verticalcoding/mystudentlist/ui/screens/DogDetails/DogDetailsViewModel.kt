package com.verticalcoding.mystudentlist.ui.screens.DogDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.verticalcoding.mystudentlist.DogsListApplication
import com.verticalcoding.mystudentlist.data.DogsPhotosRepository
import com.verticalcoding.mystudentlist.model.DogPhoto
import kotlinx.coroutines.launch

class DogDetailsViewModel(
    private val dogsPhotosRepository: DogsPhotosRepository
) : ViewModel() {

    sealed interface UiState {
        data class Success(val photo: DogPhoto): UiState
        object Error: UiState
        object Loading: UiState
    }

    var uiState: UiState by mutableStateOf(UiState.Loading); private set

    init {
        getDogImage()
    }

    fun getDogImage() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val image = dogsPhotosRepository.getRandomDogImage()
                UiState.Success(image)
            } catch (e: Exception) {
                UiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DogsListApplication)
                val dogsPhotosRepository = application.container.dogsPhotosRepository
                DogDetailsViewModel(dogsPhotosRepository)
            }
        }
    }
}