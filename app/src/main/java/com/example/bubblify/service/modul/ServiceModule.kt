package com.example.bubblify.service.modul

import com.example.bubblify.service.AccountService
import com.example.bubblify.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun accountService(auth: FirebaseAuth): AccountService {
        return AccountService(auth)
    }

    @Provides
    @Singleton
    fun storageService(
        firestore: FirebaseFirestore,
        accountService: AccountService
    ): StorageService {
        return StorageService(firestore, accountService)
    }

}
