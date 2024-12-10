package edu.itvo.pets.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.itvo.pets.presentation.composables.Pet
import edu.itvo.pets.presentation.screens.ListPetScreen
import edu.itvo.pets.presentation.screens.RecycleBinScreen
import edu.itvo.pets.presentation.ui.theme.PetsTheme
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetsTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route ?: "pets"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    when (currentRoute) {
                                        "pets" -> "Mascotas"
                                        "add" -> "Agregar Mascota"
                                        "recycle" -> "Papelera de Reciclaje"
                                        else -> "Mascotas"
                                    }
                                )
                            },
                            navigationIcon = {
                                if (currentRoute != "pets") {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Regresar"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Filled.Pets,
                                            contentDescription = "Pets icon"
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute == "pets") {
                                    IconButton(
                                        onClick = {
                                            navController.navigate("add") {
                                                launchSingleTop = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add pet"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Pets, contentDescription = "Pets") },
                                label = { Text("Mascotas") },
                                selected = currentRoute == "pets",
                                onClick = {
                                    navController.navigate("pets") {
                                        popUpTo("pets") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Delete, contentDescription = "Recycle Bin") },
                                label = { Text("Papelera") },
                                selected = currentRoute == "recycle",
                                onClick = {
                                    navController.navigate("recycle") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "pets",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("pets") {
                            ListPetScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable("add") {
                            Pet(
                                modifier = Modifier.fillMaxSize(),
                                context = this@MainActivity,
                                onSaveSuccess = { navController.navigateUp() }
                            )
                        }
                        composable("recycle") {
                            RecycleBinScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable(
                            route = "edit/{petId}",
                            arguments = listOf(
                                navArgument("petId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val petId = backStackEntry.arguments?.getInt("petId") ?: 0
                            Pet(
                                modifier = Modifier.fillMaxSize(),
                                context = this@MainActivity,
                                petId = petId,
                                onSaveSuccess = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}