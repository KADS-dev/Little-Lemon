package org.hoshiro.littlelemon.ui.user_configuration

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserConfigurationScreen(
    modifier: Modifier,
    userConfigurationViewModel: UserConfigurationViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,

) {
    Button(
        onClick = {
            userConfigurationViewModel
                .logOut(onNavigateToLogin = {onNavigateToLogin()} )
        }
    ) {
        Text("log Out")
    }


}