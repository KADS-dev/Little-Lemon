package org.hoshiro.littlelemon.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.hoshiro.littlelemon.ui.login.LoginScreen
import org.hoshiro.littlelemon.ui.login.LoginViewModel
import org.hoshiro.littlelemon.ui.home.HomeScreen
import org.hoshiro.littlelemon.ui.home.HomeViewModel
import org.hoshiro.littlelemon.ui.user_configuration.UserConfigurationScreen
import org.hoshiro.littlelemon.ui.user_configuration.UserConfigurationViewModel


@Composable
fun ContentWrapper(
    navigationController: NavHostController,
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    userConfigurationViewModel: UserConfigurationViewModel,
    //activity: Activity
){
    NavHost(
        modifier = modifier,
        navController = navigationController,
        startDestination = Routes.LoginScreen,
    ){

        composable <Routes.LoginScreen>{
            LoginScreen(
                modifier = modifier,
                loginViewModel = loginViewModel,
                onNavigateToHome = {
                    navigationController.navigate(Routes.HomeScreen){
                        popUpTo(Routes.LoginScreen){ inclusive = true }
                    }
                },
                //activity = activity,
            )
        }

        composable <Routes.HomeScreen>{
            HomeScreen(
                modifier = modifier,
                homeViewModel = homeViewModel,
                onNavigateToUserConfiguration = {

                    navigationController.navigate(route = Routes.UserConfigurationScreen){
                        //popUpTo(Routes.HomeScreen){ inclusive = true }
                    }

                },
            )
        }

        composable <Routes.UserConfigurationScreen>{
            UserConfigurationScreen(
                modifier = modifier,
                userConfigurationViewModel = userConfigurationViewModel,
                onNavigateToLogin = {
                    navigationController.navigate(Routes.LoginScreen){
                        popUpTo(route = Routes.HomeScreen) {
                            inclusive = true // Pop the start destination itself
                            // saveState = false // Optional: Don't save state of popped screens
                        }
                        launchSingleTop = true // Ensure LoginScreen is single top
                    // popUpTo(route = Routes.UserConfigurationScreen){ inclusive = true }

                    }
                },
                onNavigateToHome = {
                    navigationController.navigate(Routes.HomeScreen){
                        popUpTo(route = Routes.UserConfigurationScreen){ inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }


    }


}

sealed class Routes{

    @Serializable
    data object LoginScreen: Routes()

    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data object UserConfigurationScreen: Routes()

}
