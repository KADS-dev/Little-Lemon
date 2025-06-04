package org.hoshiro.littlelemon.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.hoshiro.littlelemon.ui.core.Routes

@Composable
fun HomeScreen(modifier: Modifier,
               homeViewModel: HomeViewModel,
               onNavigateToUserConfiguration: () -> Unit,) {
    Column() {
        Button(onClick = onNavigateToUserConfiguration) { Text("User Configuration") }
    }
}