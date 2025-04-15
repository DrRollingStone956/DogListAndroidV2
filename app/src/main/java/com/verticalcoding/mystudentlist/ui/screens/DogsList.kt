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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.verticalcoding.mystudentlist.model.DogCreate
import com.verticalcoding.mystudentlist.model.DogDetailsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsList(
    name: String,
    //breed: String,

    dogs: List<String>,
    navController: NavController,
    uiState: DogsListViewModel.UiState,
    retryAction: () -> Unit,
    onNameChange: (String) -> Unit,
    //onBreedChange: (String) -> Unit,
    onDeleteDog: (String) -> Unit
) {
    // liked dogs
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
                //OutlinedTextField(
                //    value = breed,
                 //   onValueChange = { onBreedChange(it) }
                //)
                OutlinedButton(
                    onClick = {
                        navController.navigate(DogCreate())
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
                Log.d("DogsList", "Dogs: $dogs")
                items(dogs.toList()) { dogName ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                navController.navigate(DogDetailsScreen(dogName))
                            }
                    ) {
                        ContentScreen(uiState, retryAction)
                        Text(
                            text = dogName,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.weight(1f))

                        IconButton(onClick = {
                            onDeleteDog(dogName)
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
@Composable
fun ContentScreen(uiState: DogsListViewModel.UiState, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    when(uiState) {
        is DogsListViewModel.UiState.Loading -> LoadingScreen(
            modifier = modifier
        )
        is DogsListViewModel.UiState.Error -> ErrorScreen(
            retryAction = retryAction, modifier = modifier
        )
        is DogsListViewModel.UiState.Success -> {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().padding(32.dp).height(300.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(uiState.photo.message)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }

    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.Warning, contentDescription = ""
        )
        Text(text = "Failed", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Image(imageVector = Icons.Default.Refresh, contentDescription = null)
        }
    }
}