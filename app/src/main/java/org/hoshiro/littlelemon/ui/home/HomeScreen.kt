package org.hoshiro.littlelemon.ui.home


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.webauthn.Cbor
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.hoshiro.littlelemon.R
import org.hoshiro.littlelemon.data.ofline.MenuItemRoom
import org.hoshiro.littlelemon.ui.core.Routes

@Composable
fun HomeScreen(modifier: Modifier,
               homeViewModel: HomeViewModel,
               onNavigateToUserConfiguration: () -> Unit,
               ) {
    LaunchedEffect(true) {
        //homeViewModel.getAllMenuFromDatabase()
    }
//    val databaseMenuItems = database.menuItemDao().getAll().observeAsState(emptyList()).value

    val itemsMenu = homeViewModel.menuItems.collectAsState().value
    Log.i("ITEMS", "ITEMS HomeScreen: " + itemsMenu.toString())
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            HeaderHomeScreen(onNavigateToUserConfiguration = onNavigateToUserConfiguration)

        }
        item {
            Box(modifier= Modifier
                .background(color = Color.Yellow)
                .padding(16.dp)
                .fillMaxSize()
                .size(250.dp)
            )
        }

        item{
//            MenuItemsList(
//                itemsMenu = itemsMenu,
//                modifier = Modifier
//            )
        }
    }



}


@Composable
fun HeaderHomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToUserConfiguration: () -> Unit,
) {

    Column(modifier = Modifier.fillMaxWidth()){


        Button(
            onClick = onNavigateToUserConfiguration,
            modifier= Modifier.align(Alignment.End)
        ) { Text("User Configuration") }
        Box(modifier.size(256.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Inside
                )
            }


    }
}



@Composable
private fun MenuItemsList(
    modifier: Modifier,
    itemsMenu: List<MenuItemRoom>,
) {

    LazyColumn(
        modifier = modifier
            .padding(top = 20.dp)
    ) {
        if (itemsMenu.isEmpty()){
            item {
                Box(modifier.size(20.dp))
            }
        }else{
            items(
                items = itemsMenu,
                key = { it.id },
                itemContent = { menuItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = menuItem.image.toString(),
                            contentDescription = null,
                        )
                        Text(menuItem.title)
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp),
                            textAlign = TextAlign.Right,
                            text = "%.2f".format(menuItem.price)
                        )
                    }
                }
            )
        }

    }
}


