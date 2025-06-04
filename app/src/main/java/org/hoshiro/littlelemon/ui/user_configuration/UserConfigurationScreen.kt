package org.hoshiro.littlelemon.ui.user_configuration

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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

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

//        TopAppBar(
//            modifier = Modifier,
//            title = {"User Configuration"},
//            navigationIcon = {Icons.AutoMirrored.Filled.ArrowBack},
//            actions = {},
//            //expandedHeight = ,
//            colors = TopAppBarColors(
//                containerColor = Color.Gray,
//                scrolledContainerColor = Color.Gray,
//                navigationIconContentColor = Color.Gray,
//                titleContentColor = Color.Yellow,
//                actionIconContentColor = Color.Gray
//            ),
//        )
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
        Box(Modifier.size(256.dp).align(Alignment.CenterHorizontally). background(Color.Yellow))

        Text(text = userID.value, modifier= Modifier.align(alignment = Alignment.CenterHorizontally))

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