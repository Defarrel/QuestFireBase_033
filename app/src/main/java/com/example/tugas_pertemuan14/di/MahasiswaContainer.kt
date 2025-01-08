package com.example.tugas_pertemuan14.di

import com.example.tugas_pertemuan14.repository.NetworkRepositoryMhs
import com.example.tugas_pertemuan14.repository.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val repositoryMhs: RepositoryMhs
}

class MahasiswaContainer : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firestore)
    }
}