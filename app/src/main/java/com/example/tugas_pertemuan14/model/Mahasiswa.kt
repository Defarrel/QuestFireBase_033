package com.example.tugas_pertemuan14.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val kelas: String,
    val angkatan: String,
    val skripsi: String,
    val dosenbimbing1: String,
    val dosenbimbing2: String
){
    constructor() : this("","","","","","","","","")
}
