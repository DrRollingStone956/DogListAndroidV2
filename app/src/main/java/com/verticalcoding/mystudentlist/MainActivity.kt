package com.verticalcoding.mystudentlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.verticalcoding.mystudentlist.model.DogDetailsScreen
import com.verticalcoding.mystudentlist.model.DogList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.verticalcoding.mystudentlist.ui.screens.DogCreate.DogCreateViewModel
import com.verticalcoding.mystudentlist.ui.screens.DogCreate.DogCreateScreen
import com.verticalcoding.mystudentlist.ui.screens.DogDetails.DogDetailsScreen
import com.verticalcoding.mystudentlist.ui.screens.DogDetails.DogDetailsViewModel
import com.verticalcoding.mystudentlist.model.DogCreate
import com.verticalcoding.mystudentlist.ui.screens.DogList.DogsListViewModel
import com.verticalcoding.mystudentlist.ui.screens.DogList.DogsScreen
import com.verticalcoding.mystudentlist.ui.screens.Profile.ProfileScreen
import com.verticalcoding.mystudentlist.ui.screens.Settings.SettingsScreen
import com.verticalcoding.mystudentlist.ui.theme.MyDogListTheme

class MainActivity : ComponentActivity() {

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
                            navigationController,
                            viewModel
                        )
                    }
                    composable<DogCreate> {
                        val viewModel: DogCreateViewModel =
                            viewModel(factory = DogCreateViewModel.Factory)
                        DogCreateScreen(
                            viewModel.uiState,
                            viewModel::getDogImage,
                            navigationController,
                            viewModel
                        )
                    }
                    composable("settings") { SettingsScreen(navigationController) }
                    composable("profile") { ProfileScreen(navigationController) }
                }
            }
        }
    }
}



