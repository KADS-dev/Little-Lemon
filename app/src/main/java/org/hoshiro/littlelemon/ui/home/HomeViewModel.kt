package org.hoshiro.littlelemon.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.hoshiro.littlelemon.data.ofline.MenuItemRoom
import javax.inject.Inject
import org.hoshiro.littlelemon.data.online.MenuRemoteRepository
import org.hoshiro.littlelemon.data.online.MenuDAORepository

@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val menuDatabase: MenuDatabase,
    private val menuRemoteRepository: MenuRemoteRepository,
    private val menuDAORepository: MenuDAORepository,
    //private val menuItemDao: MenuItemDao,
   // private val httpRequest: HttpClient,
): ViewModel() {
    init {
        //saveMenuToDatabase()
        //viewModelScope.launch{ }
        saveMenuToDatabase()
       // getAllMenuFromDatabase()
    }


    // Expone el flujo de items del menú como un StateFlow para la UI
    val menuItems: StateFlow<List<MenuItemRoom>> = menuDAORepository.getAllMenuItemsStream()
        .stateIn(
            scope = viewModelScope, // El scope de la corutina del ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Cuándo empezar y parar de colectar
            initialValue = emptyList() // Valor inicial mientras se carga
        )



    // Expone el flujo de items del menú como un StateFlow para la UI
//    val menuItems: StateFlow<List<MenuItemRoom>> = menuDAORepository.getAllMenuItemsStream()
//        .stateIn(
//            scope = viewModelScope, // El scope de la corutina del ViewModel
//            started = SharingStarted.WhileSubscribed(5000), // Cuándo empezar y parar de colectar
//            initialValue = emptyList() // Valor inicial mientras se carga
//        )



private var _itemsMenu = MutableStateFlow<List<MenuItemRoom>>(listOf())
val itemsMenu: StateFlow<List<MenuItemRoom>> = _itemsMenu

//    private var _itemsMenu = MutableStateFlow<Boolean>(value = false)
//    val itemsMenu: StateFlow<Boolean> = _itemsMenu

//    fun getAllMenuFromDatabase(){ //: Flow<List<MenuItemRoom>>
//        viewModelScope.launch (Dispatchers.IO){
//             _itemsMenu = menuDAORepository.getAllMenuItemsStream() as MutableStateFlow<List<MenuItemRoom>>
//        }
//    }



    private fun saveMenuToDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            menuDAORepository.fetchAndStoreNewMenuItemsIfEmpty(menuRemoteRepository.fetchMenu()!!)
        }
    }

}


//    private suspend fun fetchMenu(): List<MenuItemNetwork>? {
//        // data URL: https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json
//        val response: MenuNetwork? =
//            httpRequest.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
//                .body<MenuNetwork>()
//        return response?.menu ?: listOf( element = MenuItemNetwork(0,"", "", "", ""))
//    }


