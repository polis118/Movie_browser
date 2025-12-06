package com.example.moviebrowser.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailScreen(vm: DetailViewModel = viewModel(), movieId: String?) {
    LaunchedEffect(movieId) {
        if (movieId != null) {
            vm.loadMovie(movieId)
        }
    }

    if (vm.isLoading.value) {
        CircularProgressIndicator()
    } else {
        vm.movie.value?.let { movie ->
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(movie.poster),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Title: ${movie.title}", modifier = Modifier.padding(top = 8.dp))
                Text(text = "Year: ${movie.year}", modifier = Modifier.padding(top = 4.dp))
                Text(text = "Genre: ${movie.genre}", modifier = Modifier.padding(top = 4.dp))
                Text(text = "Director: ${movie.director}", modifier = Modifier.padding(top = 4.dp))
                Text(text = "Actors: ${movie.actors}", modifier = Modifier.padding(top = 4.dp))
                Text(text = "Plot: ${movie.plot}", modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}
