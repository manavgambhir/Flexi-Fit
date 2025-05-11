package com.example.flexifit.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flexifit.data.models.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class OnboardingViewModel: ViewModel() {
    var name by mutableStateOf("")
    var dob by mutableStateOf("")
    var gender by mutableStateOf("")
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var strengthExperience by mutableStateOf("")
    private val uid = Firebase.auth.currentUser?.uid
//    var injuryInfo by mutableStateOf("")

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
            weight = weight.toFloat(),
            height = height.toFloat(),
            strengthExperience = strengthExperience.trim()
        )
        Log.d("onBoardingTest","converting to UserProfile $up")
        return up
    }

    fun saveProfileToFirestore(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userProfile = toUserProfile()
        Log.d("onBoardingTest","saving to firestore $userProfile")

        if (uid == null) {
            Log.d("onBoardingTest","uid is null")
            onFailure(Exception("User not logged in"))
            return
        }

        if (userProfile != null) {
            Firebase.firestore.collection("users")
                .document(uid)
                .set(userProfile)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onFailure(exception) }
            Log.d("onBoardingTest","saved to firestore")
        }
        else{
            Log.d("onBoardingTest","user profile is null")
            onFailure(Exception("Incomplete or invalid profile data"))
        }
    }
}