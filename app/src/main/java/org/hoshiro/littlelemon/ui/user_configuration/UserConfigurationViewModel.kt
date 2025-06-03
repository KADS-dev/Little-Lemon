package org.hoshiro.littlelemon.ui.user_configuration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.hoshiro.littlelemon.data.AuthService
import javax.inject.Inject

@HiltViewModel
class UserConfigurationViewModel @Inject constructor(private val authService: AuthService): ViewModel() {
    fun trash(){
        Log.i("TRASH","This is a trash function")
    }
    fun logOut(onNavigateToLogin:() -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            authService.logOut()
        }
        onNavigateToLogin()
    }
}