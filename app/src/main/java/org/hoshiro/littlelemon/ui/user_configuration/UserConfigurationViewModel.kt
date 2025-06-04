package org.hoshiro.littlelemon.ui.user_configuration

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.hoshiro.littlelemon.data.AuthService
import javax.inject.Inject

@HiltViewModel
class UserConfigurationViewModel @Inject constructor(private val authService: AuthService): ViewModel() {
    private var _user = MutableStateFlow<String>(value = "")
    val user: StateFlow<String> = _user


    fun getUserID(){
        viewModelScope.launch (Dispatchers.IO){
            _user.value = authService.getCurrentUser().toString()
        }
    }

    fun logOut(onNavigateToLogin:() -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            authService.logOut()
        }
        onNavigateToLogin()
    }
}