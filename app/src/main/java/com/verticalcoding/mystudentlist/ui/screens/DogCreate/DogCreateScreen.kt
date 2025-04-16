package com.verticalcoding.mystudentlist.ui.screens.DogCreate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.verticalcoding.mystudentlist.ui.screens.DogsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogCreateScreen(
    uiState: DogCreateViewModel.UiState,
    retryAction: () -> Unit,
    navController: NavController
) {
    var dogname by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Dodaj Psa")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },

            )
        }
    ) { inner ->
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(inner)){

            ContentScreen(uiState, retryAction)
            OutlinedTextField(
                value = dogname,
                onValueChange = { dogname = it },
                label = { Text("Imie") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).padding(horizontal = 32.dp)
            )
            OutlinedTextField(
                value = breed,
                label = { Text("Rasa") },
                onValueChange = { breed = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp).padding(horizontal = 32.dp)
            )
            Button(onClick = { ;navController.popBackStack() }, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp))
            {
                Text(text = "Add", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }

}

@Composable
fun ContentScreen(uiState: DogCreateViewModel.UiState, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    when(uiState) {
        is DogCreateViewModel.UiState.Loading -> LoadingScreen(
            modifier = modifier
        )
        is DogCreateViewModel.UiState.Error -> ErrorScreen(
            retryAction = retryAction, modifier = modifier
        )
        is DogCreateViewModel.UiState.Success -> {
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