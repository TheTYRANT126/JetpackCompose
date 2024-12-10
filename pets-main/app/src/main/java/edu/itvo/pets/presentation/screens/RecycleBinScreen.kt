package edu.itvo.pets.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.itvo.pets.presentation.composables.DeletedPetCard
import edu.itvo.pets.presentation.viewmodel.RecycleBinViewModel

@Composable
fun RecycleBinScreen(
    viewModel: RecycleBinViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val deletedPetsState = viewModel.deletedPetsState.collectAsState().value
    val errorState = viewModel.errorState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorState != null) {
            Text(
                text = errorState,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (deletedPetsState == null) {
            CircularProgressIndicator()
        } else {
            if (deletedPetsState.data.isEmpty()) {
                Text(
                    text = "No deleted pets",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(deletedPetsState.data) { pet ->
                        DeletedPetCard(
                            pet = pet,
                            onRestore = { viewModel.onRestoreClicked(pet.id) }
                        )
                    }
                }
            }
        }
    }
}