package org.hoshiro.littlelemon.ui.login.email

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ValidatingInputEmailTextField(
    email: String,
    updateState: (String) -> Unit,
    validatorHasErrors: Boolean,
    //updateFinalEmailValueState: (Boolean) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        value = email,
        onValueChange = updateState,
        label = { Text("Email") },
        isError = validatorHasErrors,
        supportingText = {
            if (validatorHasErrors) {
                Text("Incorrect email format.")
            }else{
                //updateFinalEmailValueState(true)
            }
        }
    )
}

@Composable
fun PasswordTextField(
    showPassword: Boolean,
    updateState: (String) -> TextFieldState,
    toShowPassword: (Boolean) -> Unit,
    ) {

    val state = remember { TextFieldState() }

    updateState(state.text.toString())

    BasicSecureTextField(
        state = state,
        textObfuscationMode =
            if (showPassword) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 12.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp)),

        decorator = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp, end = 48.dp)
                ) {
                    innerTextField()
                }
                Icon(
                    if (showPassword) {
                        Icons.Filled.FavoriteBorder
                    } else {
                        Icons.Filled.Favorite
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .requiredSize(48.dp).padding(16.dp)
                        .clickable { toShowPassword(!showPassword) }
                )
            }
        }
    )
}