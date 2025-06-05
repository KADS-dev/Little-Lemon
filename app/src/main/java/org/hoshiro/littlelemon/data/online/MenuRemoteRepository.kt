package org.hoshiro.littlelemon.data.online

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

//interface IMenuRemoteRepository { // Optional: Define an interface for testability/abstraction
//    //fun getMenuItemsStream(): Flow<Result<List<RemoteMenuItem>>> // Using Result wrapper for error handling
//    suspend fun fetchMenu(): List<MenuItemNetwork>?
//}

@Singleton // Or @ViewModelScoped, depending on its lifecycle needs
class MenuRemoteRepository @Inject constructor(
    private val httpClient: HttpClient, // Hilt injects the HttpClient here
){

    private val baseUrl = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json" // Replace with your actual base URL

suspend fun fetchMenu(): List<MenuItemNetwork>? {
        // data URL: https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json
        val response: MenuNetwork? =
            httpClient.get(baseUrl)
                .body<MenuNetwork>()
    Log.i("DATA", response?.menu.toString())
        return response?.menu ?: listOf( element = MenuItemNetwork(0,"", "", 0.0, "", ""))
    }

}

