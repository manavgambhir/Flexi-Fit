package com.example.flexifit.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexifit.data.models.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OnboardingViewModel: ViewModel() {
    var name by mutableStateOf("")
    var dob by mutableStateOf("")
    var gender by mutableStateOf("")
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var strengthExperience by mutableStateOf("")
//    var injuryInfo by mutableStateOf("")

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    // null = no check done, true = exists, false = not found
    private val _userProfileExists = MutableStateFlow<Boolean?>(null)
    val userProfileExists: StateFlow<Boolean?> = _userProfileExists.asStateFlow()

    fun saveProfileToFirestore(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userProfile = toUserProfile()

        val uid = Firebase.auth.currentUser?.uid
        if (uid == null) {
            onFailure(Exception("User not logged in"))
            return
        }

        if (userProfile != null) {
            Firebase.firestore.collection("users")
                .document(uid)
                .set(userProfile)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onFailure(exception) }
        }
        else{
            onFailure(Exception("Incomplete or invalid profile data"))
        }
    }

    fun findUidFromFirestore(uid: String) {
        viewModelScope.launch {
            try {
                val doc = Firebase.firestore.collection("users").document(uid).get().await()
                if (doc.exists()) {
                    // Profile exists
                    _userProfileExists.value = true
                    _userProfile.value = doc.toObject(UserProfile::class.java)
                } else {
                    // Profile doesn't exist
                    _userProfileExists.value = false
                    _userProfile.value = null
                }
            } catch (e: Exception) {
                // Handle error
                _userProfileExists.value = null
                _userProfile.value = null
                Log.e("SignInFlow", "Error checking profile", e)
            }
        }
    }

    fun toUserProfile(): UserProfile? {
        if (name.isBlank() || dob.isBlank() || gender.isBlank() ||
            weight.toFloatOrNull() == null || height.toFloatOrNull() == null ||
            strengthExperience.isBlank()
        ) {
            return null
        }

        val up = UserProfile(
            name = name.trim(),
            dob = dob.trim(),
            gender = gender.trim(),
            weight = weight.toDouble(),
            height = height.toDouble(),
            strengthExperience = strengthExperience.trim()
        )
        return up
    }
}