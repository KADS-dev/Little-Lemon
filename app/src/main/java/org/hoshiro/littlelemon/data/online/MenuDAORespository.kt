package org.hoshiro.littlelemon.data.online

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.hoshiro.littlelemon.data.ofline.MenuItemDao

import javax.inject.Inject
import javax.inject.Singleton
import org.hoshiro.littlelemon.data.ofline.MenuItemRoom

@Singleton // Or @ViewModelScoped if only used by ViewModels and you want that lifecycle
class MenuDAORepository @Inject constructor(
    private val menuItemDao: MenuItemDao // Hilt injects the DAO here
) {

    fun getAllMenuItemsStream(): Flow<List<MenuItemRoom>> {
        return menuItemDao.getAll()
    }

    suspend fun fetchAndStoreNewMenuItemsIfEmpty(menuItemsNetwork: List<MenuItemNetwork>) {
        // Example: Logic to fetch from network and store if DB is empty
        // For simplicity, let's assume we just insert some dummy data if empty
        // val currentItems = menuItemDao.getAll().firstOrNull() // Example, might need to run in a dispatcher
        if (menuItemDao.isEmpty()) {
            val dummyItems =
                MenuItemRoom(
                    id = 0,
                    title = "",
                    description = "",
                    price = 0.0,
                    image = "",
                    category = "",
                )

            val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
            menuItemDao.insertAll(*menuItemsRoom.toTypedArray())
            Log.i("DATADAO", menuItemsRoom.toString())



        }
    }
}