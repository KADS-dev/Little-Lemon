package org.hoshiro.littlelemon.ui.user_configuration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.hoshiro.littlelemon.ui.theme.LightYellow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hoshiro.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserConfigurationScreen(
    modifier: Modifier,
    userConfigurationViewModel: UserConfigurationViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,

) {
    LaunchedEffect(true) {
        userConfigurationViewModel.getUserID()
    }
    Column(modifier.fillMaxSize().padding(6.dp)) {

        val userID = userConfigurationViewModel.user.collectAsState()

        IconButton(
            onClick = {
                userConfigurationViewModel
                    .navigateToHome(onNavigateToHome = onNavigateToHome )
            }
        ) {


            Icon(
                rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                contentDescription = "Localized description",
                //tint = { Color.Red }
            )

        }
        Box(
            Modifier
                .size(256.dp)
                .align(Alignment.CenterHorizontally)
                .background(color=LightYellow),
            contentAlignment = Alignment.Center

        ){
            Column( modifier = Modifier.fillMaxWidth()
            ){
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                    ,
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.lemon_pp),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Inside
                )
            }
        }

        Text(
            text = "USER: " +  userID.value,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier= Modifier.padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )

        Button(
            onClick = {
                userConfigurationViewModel
                    .logOut(onNavigateToLogin = {onNavigateToLogin()} )
            },
            modifier= Modifier.align (alignment = Alignment.End)


        ) {
            Text("log Out")
        }

    }


}