package edu.itvo.pets.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.pets.data.models.PetResponse
import edu.itvo.pets.domain.usecases.PetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecycleBinViewModel @Inject constructor(
    private val petUseCase: PetUseCase
) : ViewModel() {

    private val _deletedPetsState = MutableStateFlow<PetResponse?>(null)
    val deletedPetsState: StateFlow<PetResponse?> = _deletedPetsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    init {
        loadDeletedPets()
    }

    private fun loadDeletedPets() {
        viewModelScope.launch {
            try {
                petUseCase.getDeletedPets().collect { response ->
                    _deletedPetsState.value = response
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun onRestoreClicked(petId: Int) {
        viewModelScope.launch {
            try {
                petUseCase.restore(petId)
                loadDeletedPets()
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}