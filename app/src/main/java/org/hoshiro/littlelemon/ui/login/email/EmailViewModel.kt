package org.hoshiro.littlelemon.ui.login.email

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.toString

@HiltViewModel
class EmailViewModel @Inject constructor() : ViewModel() {
    //var email by mutableStateOf("tests@hotmail.com")
    var email by mutableStateOf("")
        private set
    var showPassword by mutableStateOf(false)
        private set
    var passwordHasErrors by mutableStateOf(true)
        private set


    private var _formComplete = MutableStateFlow<Boolean>(value = false)
    val formComplete: StateFlow<Boolean> = _formComplete

    private var _password = MutableStateFlow<String>(value = "")
    val password: StateFlow<String> = _password


    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }
//    val passwordHasErrors by derivedStateOf {
//
//        if (password.value.length < 6) {
//            true
//        } else {
//            false
//        }
//
//    }

    fun checkIPasswordHasErrors(){
        passwordHasErrors = (password.value.toString().length < 6)
    }

    fun updateEmail(input: String) {
        email = input
        updateFormComplete(
            password.value.isNotEmpty()
                    && email.isNotEmpty()
                    && !emailHasErrors
                    && !passwordHasErrors
        )

        checkIPasswordHasErrors()

        Log.i("STATE", "is email invalid (EMAIL): " + emailHasErrors.toString())
        Log.i("STATE", "password has errors (EMAIL): " + passwordHasErrors.toString())

    }

    fun updatePassword(input: String): TextFieldState  {
        _password.value = input

        checkIPasswordHasErrors()

        updateFormComplete(
            password.value.isNotEmpty()
                && email.isNotEmpty()
                    && !emailHasErrors
                        && !passwordHasErrors
        )
        Log.i("STATE", "is email valid (PASSWORD): " + emailHasErrors.toString())
        Log.i("STATE", "password has errors (PASSWORD): " + passwordHasErrors.toString())
        Log.i("STATE", "password length (PASSWORD): " + password.value.toString().length)
        return TextFieldState(password.value)


    }

    fun updateShowPassword(input: Boolean){
        showPassword = input
    }


    fun updateFormComplete(isComplete: Boolean){
        _formComplete.value = isComplete
    }


}

