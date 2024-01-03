package com.example.bubblify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel
@Inject
constructor(
    private val application: Application,
) : AndroidViewModel(application = application) {

}