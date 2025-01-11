package com.example.tugas_pertemuan14.repository

import com.example.tugas_pertemuan14.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkRepositoryMhs (
    private val fireStore: FirebaseFirestore
): RepositoryMhs{
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        try{
            fireStore.collection("Mahasiswa")
                .add(mahasiswa)
                .await()
        }catch (e: Exception){
            throw Exception("Gagal menambahkan data mahasiswa: ${e.message}")
        }
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = fireStore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            //method yang disediakan untuk
            .addSnapshotListener{value, error, ->
                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)
                    }
                    trySend(mhsList) //try send memberikan fungsi untuk mengirimkan data ke flow
                }
            }
        awaitClose()
        mhsCollection.remove()
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> = callbackFlow{
        val mhsDocumented = fireStore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener{value, error ->
                if (value != null) {
                    val mhs = value.toObject(Mahasiswa::class.java)!!
                    trySend(mhs)
                }
            }
        awaitClose{
            mhsDocumented.remove()
        }
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        try{
            fireStore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .delete()
                .await()
        }catch (e: Exception){
            throw Exception("Gagal menghapus data mahasiswa: ${e.message}")
        }
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        try{
            fireStore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        }catch (e: Exception){
            throw Exception("Gagal menghapus data mahasiswa: ${e.message}")
        }
    }

}