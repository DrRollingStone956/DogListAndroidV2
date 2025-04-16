package com.verticalcoding.mystudentlist.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.verticalcoding.mystudentlist.data.DogsRepository
import com.verticalcoding.mystudentlist.model.Dog
import com.verticalcoding.mystudentlist.model.DogDetailsScreen
import com.verticalcoding.mystudentlist.model.DogScreen

@Composable
fun DogsScreen(
    viewModel: DogsListViewModel,
    navigationController: NavController
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is DogsListViewModel.UiState.Success) {
        DogsList(
            name = viewModel.name.value,
            dogs = (items as DogsListViewModel.UiState.Success).data,
            navController = navigationController,
            onNameChange = { viewModel.name.value = it},
            onSave = viewModel::addDog,
            onFav = viewModel::triggerFav,
            onTrash = viewModel::removeDog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsList(
    name: String,
    dogs: List<Dog>,
    navController: NavController,
    onNameChange: (name: String) -> Unit,
    onSave: (name: String) -> Unit,
    onFav: (id:Int) -> Unit,
    onTrash: (id:Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Doggos", fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ){
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                OutlinedTextField(
                    value = name,
                    onValueChange = { onNameChange(it) }
                )

                OutlinedButton(
                    enabled = name.isNotEmpty() && name.length > 3,
                    onClick = {
                        onSave(name)
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top=16.dp)
            ) {
                items(dogs) { dog ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                navController.navigate(DogScreen(dog.name))
                            }
                    ) {
                        Text(
                            text = dog.name,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.weight(1f))

                        val brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF6A5ACD), // Purple
                                Color(0xFFFFC0CB)  // Pink
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(50f, 100f)
                        )

                        IconButton(
                            onClick = {
                                onFav(dog.id)
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .graphicsLayer(alpha = 0.99f)
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(brush, blendMode = BlendMode.SrcAtop)
                                        }
                                    },
                                imageVector = if (dog.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                            )
                        }

                        IconButton(onClick = {
                            onTrash(dog.id)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}