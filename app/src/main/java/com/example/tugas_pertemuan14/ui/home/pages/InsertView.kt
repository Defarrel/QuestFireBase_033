package com.example.tugas_pertemuan14.ui.home.pages

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tugas_pertemuan14.ui.home.viewmodel.FormErrorState
import com.example.tugas_pertemuan14.ui.home.viewmodel.FormState
import com.example.tugas_pertemuan14.ui.home.viewmodel.HomeUiState
import com.example.tugas_pertemuan14.ui.home.viewmodel.InsertUiState
import com.example.tugas_pertemuan14.ui.home.viewmodel.MahasiswaEvent



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
            label = { Text(text = "Nama") },
            singleLine = true,
            isError = errorState.nama != null,
            placeholder = { Text(text = "Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it)) },
            label = { Text(text = "NIM") },
            singleLine = true,
            isError = errorState.nim != null,
            placeholder = { Text(text = "Masukkan NIM") },
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
            label = { Text(text = "Alamat") },
            singleLine = true,
            isError = errorState.alamat != null,
            placeholder = { Text(text = "Masukkan Alamat") }
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
            label = { Text(text = "Angkatan") },
            singleLine = true,
            isError = errorState.angkatan != null,
            placeholder = { Text(text = "Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}