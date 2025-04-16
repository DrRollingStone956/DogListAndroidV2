package com.verticalcoding.mystudentlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.verticalcoding.mystudentlist.model.DogDetailsScreen
import com.verticalcoding.mystudentlist.model.DogList
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.verticalcoding.mystudentlist.ui.screens.DogCreate.DogCreateViewModel
import com.verticalcoding.mystudentlist.ui.screens.DogCreate.DogCreateScreen
import com.verticalcoding.mystudentlist.ui.screens.DogDetails.DogDetailsScreen
import com.verticalcoding.mystudentlist.ui.screens.DogDetails.DogDetailsViewModel
import com.verticalcoding.mystudentlist.model.Dog
import com.verticalcoding.mystudentlist.model.DogCreate
import com.verticalcoding.mystudentlist.ui.screens.DogsListViewModel
import com.verticalcoding.mystudentlist.ui.screens.DogsScreen
import com.verticalcoding.mystudentlist.ui.theme.MyDogListTheme

class MainActivity : ComponentActivity() {

    private var dogs by mutableStateOf(emptyList<String>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDogListTheme {
                val navigationController = rememberNavController()
                NavHost(navController = navigationController, startDestination = DogList) {
                    composable<DogList> {
                        val viewModel: DogsListViewModel =
                            viewModel(factory = DogsListViewModel.Factory)
                        DogsScreen(
                            viewModel = viewModel,
                            navigationController = navigationController
                        )
                    }
                    composable<DogDetailsScreen> {
                        val args = it.toRoute<DogDetailsScreen>()
                        val viewModel: DogDetailsViewModel =
                            viewModel(factory = DogDetailsViewModel.Factory)
                        DogDetailsScreen(
                            args,
                            viewModel.uiState,
                            viewModel::getDogImage,
                            navigationController
                        ) {
                            dogs = dogs - it
                        }
                    }
                    composable<DogCreate> {
                        val viewModel: DogCreateViewModel =
                            viewModel(factory = DogCreateViewModel.Factory)
                        DogCreateScreen(
                            viewModel.uiState,
                            viewModel::getDogImage,
                            navigationController
                        )
                    }
                    composable("settings") { SettingsScreen(navigationController) }
                    composable("profile") { ProfileScreen(navigationController) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Ustawienia", fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Profil", fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = CircleShape)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Jan Zizka",
                fontSize = 18.sp
            )
        }
    }
}