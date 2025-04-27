package com.verticalcoding.mystudentlist.ui.screens.DogList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.verticalcoding.mystudentlist.model.Dog
import com.verticalcoding.mystudentlist.model.DogCreate
import com.verticalcoding.mystudentlist.model.DogDetailsScreen

@Composable
fun DogsScreen(
    viewModel: DogsListViewModel,
    navigationController: NavController
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is DogsListViewModel.UiState.Success) {
        DogsList(
            name = viewModel.searchText.text,
            dogs = viewModel.getFilteredDogs((items as DogsListViewModel.UiState.Success).data),
            navController = navigationController,
            onNameChange = { viewModel.onSearchTextChange(TextFieldValue(it)) },
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
                    singleLine = true,
                    label = {Text("Wyszukaj pieska")},
                    onValueChange = { onNameChange(it) }
                )



                IconButton(
                    onClick = {
                        navController.navigate(DogCreate)
                    }) {
                    Icon(
                        modifier = Modifier.padding(top = 16.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                Text(text = "🐶: ${dogs.size}")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "❤️: ${dogs.count { it.isFavorite }}")
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
                                navController.navigate(DogDetailsScreen(dog.name, dog.breed, dog.imageURL, dog.id))
                            }
                    )
                    {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(dog.imageURL)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = (-8).dp)
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp))

                        )
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ){
                            Text(
                                text = dog.name,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            Text(
                                text = dog.breed,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
                                tint = Color.Red,
                                contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}