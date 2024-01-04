package com.example.bubblify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bubblify.model.Activity
import com.example.bubblify.model.ActivityIcon
import com.example.bubblify.service.StorageService
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddActivitiesViewModel
    @Inject
    constructor(
        private val application: Application,
        private val storageService: StorageService
    ) : AndroidViewModel(application = application) {

    private val _activities = MutableLiveData<List<Activity>>()
    val activities: LiveData<List<Activity>> = _activities

    private var _activityAdded = MutableLiveData<DocumentReference?>()

    val listActivities = listOf(
        Activity(null, "Sleep", ActivityIcon.SLEEPING),
        Activity(null, "Work", ActivityIcon.WORKING),
        Activity(null, "Eat", ActivityIcon.EATING),
        Activity(null, "Sport", ActivityIcon.SPORT),
        Activity(null, "Party", ActivityIcon.PARTY),
        Activity(null, "Study", ActivityIcon.STUDY),
        Activity(null, "Chilling", ActivityIcon.CHILLING),
        Activity(null, "Bored", ActivityIcon.BORED),
        Activity(null, "Gaming", ActivityIcon.GAMING),
        Activity(null, "Cooking", ActivityIcon.COOKING),
        Activity(null, "Family time", ActivityIcon.FAMILY),
        Activity(null, "Busy", ActivityIcon.BUSY),
        Activity(null, "Shopping", ActivityIcon.SHOPPING),
        Activity(null, "Music", ActivityIcon.MUSIC),
    )

    fun fetchActivities(groupReferenceString: String) {
        viewModelScope.launch {
            try {
                val value = storageService.getActivitiesInGroup(groupReferenceString)
                _activities.value = listActivities.filter { activity ->
                    value.none { it.data.name == activity.name } }
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("AddActivitiesViewModel", "fetchActivities: ${e.message}")
            }
        }
    }

    fun addActivityToGroup(
        activity: Activity,
        groupReferenceString: String,
        callback: (DocumentReference?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val activities = storageService.getActivitiesInGroup(groupReferenceString)
                Log.d("value", activities.size.toString())
                if (activities.size < 5) {
                    _activityAdded.value = storageService.addActivityToGroup(activity, groupReferenceString)
                    callback(_activityAdded.value)
                }
                else {
                    callback(null)
                }
            } catch (e: Exception) {
                // If there is an error, log it
                Log.e("AddActivitiesViewModel", "addActivityToGroup: ${e.message}")
                callback(null)
            }
        }
    }
}