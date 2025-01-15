package com.example.tugas_pertemuan14.ui.home.pages

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas_pertemuan14.ui.PenyediaViewModel
import com.example.tugas_pertemuan14.ui.home.viewmodel.FormErrorState
import com.example.tugas_pertemuan14.ui.home.viewmodel.FormState
import com.example.tugas_pertemuan14.ui.home.viewmodel.HomeUiState
import com.example.tugas_pertemuan14.ui.home.viewmodel.InsertUiState
import com.example.tugas_pertemuan14.ui.home.viewmodel.InsertViewModel
import com.example.tugas_pertemuan14.ui.home.viewmodel.MahasiswaEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState //state utama untuk loading, success, error
    val uiEvent = viewModel.uiEvent //state utama untuk form dan validasi
    val snackbarHostState = remember { SnackbarHostState() } //state utama untuk snackbar
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Observasi peruabahan state untuk snackbar dan navigasi

    LaunchedEffect (uiState){
        when (uiState) {
            is FormState.Success -> {
                println("InsertMhsView: uiState is FormState.Success, navigate to Home"+uiState.message)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message) //tampilkan snackbar
                }
                delay(700)
                //navigasi langsung
                onNavigate()
                viewModel.resetSnackBarMessage() // Reset Snackbar state
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message) //tampilkan snackbar
                }
            }else -> Unit
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Insert Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(state = scrollState)
        ) {
            InsertBodyMhs(
                uiState = uiEvent,
                homeUiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    if (viewModel.validateFields()) {
                        viewModel.insertMhs()
                        //onNavigate()
                    }
                }
            )
        }

    }
}

@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick: () -> Unit,
    homeUiState: FormState
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading

        )   {
            if(homeUiState is FormState.Loading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text("Loading....")
            }else{
                Text("Add")
            }
        }
    }
}

@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A","B","C","D","E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama") },
            singleLine = true,
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it)) },
            label = { Text("NIM") },
            singleLine = true,
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jensis Kelamin")
        Row (
            modifier = modifier.fillMaxWidth()
        ) {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = { onValueChange(mahasiswaEvent.copy(jenisKelamin = jk)) },

                    )
                    Text(text = jk)
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { onValueChange(mahasiswaEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            singleLine = true,
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") }
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Kelas")
        Row (
            modifier = modifier.fillMaxWidth()
        ) {
            kelas.forEach { kls ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kls,
                        onClick = { onValueChange(mahasiswaEvent.copy(kelas = kls)) },

                    )
                    Text(text = kls)
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { onValueChange(mahasiswaEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            singleLine = true,
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.skripsi,
            onValueChange = { onValueChange(mahasiswaEvent.copy(skripsi = it)) },
            label = { Text("Judul Skripsi") },
            singleLine = true,
            isError = errorState.skripsi != null,
            placeholder = { Text("Masukkan Angkatan") },
        )
        Text(
            text = errorState.skripsi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.dosenbimbing1,
            onValueChange = { onValueChange(mahasiswaEvent.copy(dosenbimbing1 = it)) },
            label = { Text("Dosen Pembimbing 1") },
            singleLine = true,
            isError = errorState.dosenbimbing1 != null,
            placeholder = { Text("Masukkan Dosen Pembimbing 1") },
        )
        Text(
            text = errorState.dosenbimbing1 ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.dosenbimbing2,
            onValueChange = { onValueChange(mahasiswaEvent.copy(dosenbimbing2 = it)) },
            label = { Text("Dosen Pembimbing 2") },
            singleLine = true,
            isError = errorState.dosenbimbing2 != null,
            placeholder = { Text("Masukkan Dosen Pembimbing 2") },
        )
        Text(
            text = errorState.dosenbimbing2 ?: "",
            color = Color.Red
        )
    }
}