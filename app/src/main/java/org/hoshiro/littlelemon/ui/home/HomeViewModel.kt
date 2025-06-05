package org.hoshiro.littlelemon.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.hoshiro.littlelemon.data.ofline.MenuItemRoom
import org.hoshiro.littlelemon.data.online.MenuDAORepository
import org.hoshiro.littlelemon.data.online.MenuRemoteRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val menuRemoteRepository: MenuRemoteRepository,
    private val menuDAORepository: MenuDAORepository,

): ViewModel() {
    init {
        saveMenuToDatabase()
    }

    // Holds the currently selected category. Null or a special value means "All".
    private val _selectedCategory = MutableStateFlow<String?>("ALL") // null for "All"
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Expone el flujo de items del menú como un StateFlow para la UI
    private val _allMenuItems: StateFlow<List<MenuItemRoom>> = menuDAORepository.getAllMenuItemsStream()
        .stateIn(
            scope = viewModelScope, // El scope de la corutina del ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Cuándo empezar y parar de colectar
            initialValue = emptyList() // Valor inicial mientras se carga
        )

    // The Flow of menu items to be displayed in the UI, filtered by the selected category
    val menuItems: StateFlow<List<MenuItemRoom>> =
        combine(_allMenuItems, _selectedCategory) { allItems, category ->
            if (category == null || category == "ALL") {
                allItems
            } else
            {
                allItems.filter { it.category == category }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


private var _itemsMenu = MutableStateFlow<List<MenuItemRoom>>(listOf())
val itemsMenu: StateFlow<List<MenuItemRoom>> = _itemsMenu


    fun selectCategory(category: String?) {
       _selectedCategory.value = if (category == "ALL") null else category
    }


    private fun saveMenuToDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            menuDAORepository.fetchAndStoreNewMenuItemsIfEmpty(menuRemoteRepository.fetchMenu()!!)
        }
    }

}

