package com.example.flexifit.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexifit.LoginState
import com.example.flexifit.R
import com.example.flexifit.utils.sharedPref
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init{
        checkLoginState()
    }

    private fun checkLoginState() {
        val user = auth.currentUser
        _loginState.value = if(user!=null) LoginState.Success else LoginState.Idle
    }

    fun googleSignIn(context: Context,launcher: ManagedActivityResultLauncher<Intent,ActivityResult>?){
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try{
                val result = credentialManager.getCredential(context,request)
                val credential = result.credential
                if (credential is CustomCredential &&
                    credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

                    val authResult = auth.signInWithCredential(firebaseCredential).await()
                    val firebaseUser = authResult.user
                    if (firebaseUser != null && !firebaseUser.isAnonymous) {
                        _loginState.value = LoginState.Success
                    } else {
                        _loginState.value = LoginState.Error("User is anonymous or null")
                    }
                }
            } catch (e: NoCredentialException) {
                launcher?.launch(Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                    putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                })
            } catch (e: Exception) {
                Log.e("AuthError", "Google Sign-In error", e)
                _loginState.value = LoginState.Error(e.message ?: "Google Sign-In error")
            }
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        // TODO: Implement sign-in logic
    }

    fun signOut() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                auth.signOut()
                _loginState.value = LoginState.Idle
            } catch (e: Exception) {
                Log.e("AuthError", "Unable to sign out", e)
                _loginState.value = LoginState.Error("Sign out failed")
            }
        }
    }
}