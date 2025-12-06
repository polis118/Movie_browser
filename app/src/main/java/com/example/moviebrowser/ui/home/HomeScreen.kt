package com.example.moviebrowser.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviebrowser.data.model.Movie
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: HomeViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    vm.search(it)
                },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (vm.isLoading.value && !vm.isSearching.value) {
                LoadingState()
            } else if (vm.error.value != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = vm.error.value ?: "An error occurred.")
                }
            } else if (vm.isSearching.value) {
                if (vm.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else if (vm.searchResults.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No movies found.")
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.searchResults.value) { movie ->
                            MovieGridItem(movie = movie, onClick = {
                                navController.navigate("detail/${movie.imdbID}")
                            })
                        }
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                    item {
                        MovieCategory("Most Watched", vm.mostWatchedMovies.value, navController)
                    }
                    item {
                        MovieCategory("Popular", vm.popularMovies.value, navController)
                    }
                    item {
                        MovieCategory("New", vm.newMovies.value, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingState() {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(3) {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Box(modifier = Modifier
                    .padding(8.dp)
                    .width(150.dp)
                    .height(24.dp)
                    .shimmer() 
                    .background(Color.Gray))
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .aspectRatio(2f / 3f)
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmer()
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCategory(title: String, movies: List<Movie>, navController: NavController) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(movies) { movie ->
                MovieRowItem(movie = movie, onClick = {
                    navController.navigate("detail/${movie.imdbID}")
                })
            }
        }
    }
}

@Composable
fun MovieRowItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f), 
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
fun MovieGridItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}