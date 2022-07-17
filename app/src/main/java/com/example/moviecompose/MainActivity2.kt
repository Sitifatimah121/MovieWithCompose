package com.example.moviecompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.moviecompose.MainActivity2.Companion.KEY_MOVIE
import com.example.moviecompose.main.MainViewModel
import com.example.moviecompose.model.UserPreferences
import com.example.moviecompose.model.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.skydoves.landscapist.glide.GlideImage

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity2 : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]
        mainViewModel.getAllMovie()
        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {


//                mainViewModel.movie.observe(this) {
//                    listMovie(mainViewModel = mainViewModel, componentActivity = ComponentActivity() )
//                }
            }
        }
        setContent {
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            listMovie(mainViewModel = mainViewModel, componentActivity = this@MainActivity2)                        }
                    }


                }
            }
        }
    }


    companion object{
        val KEY_MOVIE = "key_movie"
        val KEY_ID_GENRE = "key_id_genre"
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun listMovie(mainViewModel: MainViewModel, componentActivity: ComponentActivity){
    val itemMovie = mainViewModel.movie.value
//    Log.d("TAG", "listMovie response: $itemMovie")
//    Log.d("TAG", "listMovie response1: ${itemMovie[0].getTitle()}")


    LazyVerticalGrid(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
        cells = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(itemMovie){ item ->
            MovieCard(movie = item, componentActivity)
        }

    }
}

@Composable
fun MovieCard(movie: Movie, componentActivity: ComponentActivity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                val intent = Intent(componentActivity, DetailActivity::class.java)
                intent.putExtra(KEY_MOVIE, movie)
                componentActivity.startActivity(intent)
            },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            GlideImage(
                imageModel = "https://image.tmdb.org/t/p/w342${movie.getPosterPath()}",
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.dosan),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Text(
                text = movie.getTitle().toString(),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    MovieComposeTheme {
        Movie()
    }
}