package com.example.bubblify.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bubblify.model.Group

class BubbleViewModel : ViewModel(){
    val group = Group("Erasmus Friends", 0x47382937)
}