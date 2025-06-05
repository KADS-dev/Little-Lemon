package org.hoshiro.littlelemon.ui.home


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.webauthn.Cbor
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.hoshiro.littlelemon.R
import org.hoshiro.littlelemon.data.ofline.MenuItemRoom
import org.hoshiro.littlelemon.ui.core.Routes

@Composable
fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    onNavigateToUserConfiguration: () -> Unit,
) {
    val itemsMenu = homeViewModel.menuItems.collectAsState().value

    val categories = listOf<String>("ALL","starters","desserts","mains")
    val selectedCategory = homeViewModel.selectedCategory.collectAsState().value


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
        item {
            CategorySelectionRow(
                categories = categories,
                selectedCategory = selectedCategory ?: "ALL", // Pass "All" if null
                onCategorySelected = { category ->
                    homeViewModel.selectCategory(category)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (itemsMenu.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No menu items available.")
                }
            }
        }else{
            item {
                Text(
                    "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp)
                )
            }
            items(
                items = itemsMenu,
                key = { it.id }
            ) { menuItem ->
                MenuItemRow(menuItem = menuItem)
            }
        }
        item{

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
fun CategorySelectionRow(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)) {
        Text(
            "Categories",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categories) { category ->

                CategoryButton(
                    text = category,
                    isSelected = (category == selectedCategory
                            ),
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}
@Composable
fun CategoryButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, borderColor, androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}


@Composable
private fun MenuItemRow(
    menuItem: MenuItemRoom,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = menuItem.image.toString(),
                contentDescription = menuItem.title,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    menuItem.title,
                    style = MaterialTheme.typography.bodyLarge,

                )
                Text(
                    menuItem.description,
                    style = MaterialTheme.typography.bodyMedium,

                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                textAlign = TextAlign.Right,
                text = "%.2f".format(menuItem.price) + "$",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}
