package org.hoshiro.littlelemon.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    fun isLogged() {
        //TODO (Check if the user is logged and if it is navigate to HomeScreen
    }
    fun onLoginWithEmail(){
        //TODO (Log the user with an email and password)
    }
    fun onLoginWithGoogle(){
        //TODO (Log the user with the google oauth)
    }

    fun launchCredManButtonUI(context: Context, onNavigateToHome: () -> Unit) {}
    //TODO (Log the user with the google oauth)
}