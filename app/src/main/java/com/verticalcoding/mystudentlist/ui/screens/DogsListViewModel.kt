package com.verticalcoding.mystudentlist.ui.screens


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.verticalcoding.mystudentlist.DogsListApplication
import com.verticalcoding.mystudentlist.data.DogsRepository
import com.verticalcoding.mystudentlist.model.Dog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DogsListViewModel(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    sealed interface UiState {
        object Loading: UiState
        data class Error(val throwable: Throwable): UiState
        data class Success(val data: List<Dog>): UiState
    }

    val name = mutableStateOf("Reksio")

    val uiState: StateFlow<UiState> = dogsRepository
        .dogs
        .map<List<Dog>, UiState> { UiState.Success(data = it) }
        .catch { emit(UiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    fun addDog(name: String) {
        viewModelScope.launch {
            dogsRepository.add(name)
        }
        this.name.value = ""
    }

    fun removeDog(id: Int) {
        viewModelScope.launch {
            dogsRepository.remove(id)
        }
    }

    fun triggerFav(id: Int) {
        viewModelScope.launch {
            dogsRepository.triggerFav(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DogsListApplication)
                val dogsRepository = application.container.dogsPhotosRepository
                DogsListViewModel(dogsRepository)
            }
        }
    }
}