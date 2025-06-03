package org.hoshiro.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.hoshiro.littlelemon.ui.login.LoginViewModel
import org.hoshiro.littlelemon.ui.core.ContentWrapper
import org.hoshiro.littlelemon.ui.home.HomeViewModel
import org.hoshiro.littlelemon.ui.theme.LittleLemonRestaurantTheme
import org.hoshiro.littlelemon.ui.user_configuration.UserConfigurationViewModel
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val loginViewModel : LoginViewModel by viewModels()
        val homeViewModel : HomeViewModel by viewModels()
        val userConfigurationViewModel : UserConfigurationViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            LittleLemonRestaurantTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navigationController = rememberNavController()

                    ContentWrapper(
                        modifier = Modifier.padding(innerPadding),
                        navigationController = navigationController,
                        loginViewModel = loginViewModel,
                        homeViewModel = homeViewModel,
                        userConfigurationViewModel = userConfigurationViewModel,
                       // activity = this,
                    )

                }
            }
        }
    }
}
