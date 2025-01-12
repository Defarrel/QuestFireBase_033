package com.example.tugas_pertemuan14.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas_pertemuan14.model.Mahasiswa
import com.example.tugas_pertemuan14.repository.RepositoryMhs
import com.example.tugas_pertemuan14.ui.navigation.DestinasiDetail
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val detailUiEvent: MahasiswaEvent) : DetailUiState()
    data class Error(val errorMessage: String) : DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: RepositoryMhs
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getMahasiswaByNim()
    }

    private fun getMahasiswaByNim() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                mhs.getMhs(_nim).collect { result ->
                    detailUiState = DetailUiState.Success(result.toDetailUiEvent())
                }
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}


