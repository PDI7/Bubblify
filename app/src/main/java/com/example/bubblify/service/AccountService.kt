package com.example.bubblify.service

import com.example.bubblify.model.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountService
@Inject
constructor(private val auth: FirebaseAuth) {

    val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    val hasUser: Boolean
        get() = auth.currentUser != null

    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(value = Resource.Loading())
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(value = Resource.Success(data = result))
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }

    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(value = Resource.Loading())
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(value = Resource.Success(data = result))
        }.catch {
            emit(value = Resource.Error(it.message.toString()))
        }
    }

    suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    fun signOut() {
        auth.signOut()
    }
}