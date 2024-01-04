package com.example.bubblify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.bubblify.MainState
import com.example.bubblify.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel
@Inject
constructor(
    private val application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application = application) {
    fun logout(mainState: MainState) {
        accountService.signOut()
        mainState.navigate("login")
    }

}