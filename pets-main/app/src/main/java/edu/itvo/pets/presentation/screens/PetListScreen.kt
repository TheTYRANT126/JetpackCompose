package edu.itvo.pets.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.itvo.pets.presentation.composables.PetCard
import edu.itvo.pets.presentation.viewmodel.ListPetViewModel

@Composable
fun ListPetScreen(
    viewModel: ListPetViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val petResponse = viewModel.petsState.collectAsState().value
    val errorMessage = viewModel.errorState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadPets()
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        errorMessage?.let {
            Text(text = "Error: $it", color = Color.Red)
        }

        if (petResponse == null && errorMessage == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            petResponse?.data?.let { pets ->
                if (pets.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(pets) { pet ->
                            PetCard(pet, viewModel = viewModel)
                        }
                    }
                } else {
                    // Si la lista está vacía, muestra un mensaje y una sugerencia
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No hay mascotas disponibles",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Usa el botón + para agregar una nueva mascota",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}