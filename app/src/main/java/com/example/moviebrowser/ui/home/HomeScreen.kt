package com.example.moviebrowser.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviebrowser.data.model.Movie

@Composable
fun HomeScreen(navController: NavController, vm: HomeViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    Column {
        TextField(
            value = query,
            onValueChange = {
                query = it
                vm.search(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        if (vm.isLoading.value) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(vm.movies.value) { movie ->
                    MovieListItem(movie = movie, onClick = {
                        navController.navigate("detail/${movie.imdbID}")
                    })
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = movie.title)
            Text(text = movie.year)
        }
    }
}
