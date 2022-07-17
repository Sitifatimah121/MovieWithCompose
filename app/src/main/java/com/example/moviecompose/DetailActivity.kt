package com.example.moviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecompose.MainActivity2.Companion.KEY_MOVIE
import com.example.moviecompose.model.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.skydoves.landscapist.glide.GlideImage


class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val movie = intent.getSerializableExtra(KEY_MOVIE) as Movie
                    detail(movie = movie)
                }
            }
        }
    }
}

@Composable
fun detail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        GlideImage(
            imageModel = "https://image.tmdb.org/t/p/w342${movie.getPosterPath()}",
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(id = R.drawable.dosan),
            modifier = Modifier
                .padding(6.dp)
                .height(400.dp)
                .fillMaxWidth()
        )
        Text(
            text = movie.getTitle().toString(),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = movie.getReleaseDate().toString(),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = movie.getOverview().toString(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    MovieComposeTheme {
        detail(movie = Movie())
    }
}