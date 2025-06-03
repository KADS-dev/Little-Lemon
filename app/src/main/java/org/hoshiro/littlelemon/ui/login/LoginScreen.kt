package org.hoshiro.littlelemon.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.hoshiro.littlelemon.R
import org.hoshiro.littlelemon.ui.login.email.EmailViewModel
import org.hoshiro.littlelemon.ui.login.email.PasswordTextField
import org.hoshiro.littlelemon.ui.login.email.ValidatingInputEmailTextField
import kotlin.math.log

@Composable
fun LoginScreen (
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
    //activity: Activity,
    ) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        loginViewModel.isLogged()
    }

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier.background(Color.Yellow).fillMaxWidth().weight(10f))

        EmailForm(loginViewModel)

        Spacer(Modifier.weight(1f))
    }

}

@Composable
fun EmailForm(
    loginViewModel: LoginViewModel,
    emailViewModel: EmailViewModel = viewModel<EmailViewModel>()
){
    val buttonActivate: Boolean by emailViewModel.formComplete.collectAsState()

    Column {

        ValidatingInputEmailTextField(
            email = emailViewModel.email,
            updateState = {input -> emailViewModel.updateEmail(input) },
            validatorHasErrors = emailViewModel.emailHasErrors,
            //updateFinalEmailValueState = {input -> emailViewModel.updateFinalEmailValueState(input)},
        )

        PasswordTextField(
            showPassword = emailViewModel.showPassword,
            updateState = {input -> emailViewModel.updatePassword(input)},
            toShowPassword = {input -> emailViewModel.updateShowPassword(input)},
            )

        Button(
            modifier = Modifier.padding(end=6.dp).align(Alignment.End),
            onClick = {loginViewModel.onLoginWithEmail()},
            enabled = buttonActivate
        ) { Text("Enter") }
    }


}
@Composable
fun LoginButtons(loginViewModel: LoginViewModel, context: Context, onNavigateToHome: () -> Unit){
    Column {

        Button(onClick = {}) { Text("Login with Email") }

        //Button(onClick = { loginViewModel.onLoginWithGoogle() }) { Text("Login with Google") }
        GoogleFirebaseAuthenticationButton(
            loginViewModel = loginViewModel,
            context = context,
            onNavigateToHome = onNavigateToHome,
        )
    }


}


/*** Google ***/
@Composable
fun GoogleFirebaseAuthenticationButton(
    loginViewModel: LoginViewModel,
    context: Context,
    onNavigateToHome: () -> Unit
){
    Button(

        onClick = {
            loginViewModel.viewModelScope.launch {
                loginViewModel.launchCredManButtonUI(context= context, onNavigateToHome = onNavigateToHome)
            }

        },
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp, )
            .fillMaxWidth(),
        enabled = true,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "LoginWithGoogle",
                Modifier.size(16.dp)
            )
            Spacer(Modifier.width(width = 10.dp))
            Text(text = "Login with Gmail")
        }

    )
}
