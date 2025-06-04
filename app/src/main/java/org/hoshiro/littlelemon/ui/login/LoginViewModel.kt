package org.hoshiro.littlelemon.ui.login

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.hoshiro.littlelemon.R
import org.hoshiro.littlelemon.data.AuthService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthService): ViewModel() {
    fun isLogged(navigateToDetail : () -> Unit) {
        viewModelScope.launch {
            if ( authService.isUserLogged() ) navigateToDetail()
        }
    }
    fun onLoginWithEmail(emailText: String, passwordText: String, navigateToDetail : () -> Unit){
//        Log.i("STATEB", "password: $passwordText")
//        Log.i("STATEB", "email: $emailText")
        viewModelScope.launch {
            var result = withContext(Dispatchers.IO){
                authService.register(emailText, passwordText)
            }

            if(result != null){
                navigateToDetail()
            }else{
                result = withContext(Dispatchers.IO){
                    authService.login(emailText, passwordText)
                }
                if(result != null){
                    navigateToDetail()
                }else{
                    Log.i("Error", "The user can't be registered nor logged")
                }
            }
        }

    }
    suspend fun launchCredManButtonUI(
        context: Context,
        onNavigateToHome: () -> Unit,
        ) {
        try {
            val signInWithGoogleOption = GetSignInWithGoogleOption
                .Builder(serverClientId = context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build()

            val result = CredentialManager.create(context).getCredential(
                request = request,
                context = context
            )

            onSignInWithGoogle(credential = result.credential, onNavigateToHome = onNavigateToHome)
            //onSignInWithGoogle(credential = credential, onNavigateToDetail)
        } catch (e: NoCredentialException) {
            Log.d("ERROR", "NoCredentialException: " + e.message.orEmpty())
        } catch (e: GetCredentialException) {
            Log.d("ERROR", "GetCredentialException: " + e.message.orEmpty())
        }
    }

    fun onSignInWithGoogle(credential: Credential, onNavigateToHome: () -> Unit) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                authService.signInWithGoogle(googleIdTokenCredential.idToken)
//                openAndPopUp(NOTES_LIST_SCREEN, SIGN_IN_SCREEN)
                onNavigateToHome()
            } else {
                Log.e("ERROR", "UNEXPECTED_CREDENTIAL")
            }
        }
    }

//
//    fun launchCredManButtonUI() {}
//    //TODO (Log the user with the google oauth)
}