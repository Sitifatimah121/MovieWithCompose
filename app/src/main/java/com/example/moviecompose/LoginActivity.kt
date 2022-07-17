package com.example.moviecompose

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.moviecompose.R
import com.example.moviecompose.login.LoginViewModel
import com.example.moviecompose.model.UserModel
import com.example.moviecompose.model.UserPreferences
import com.example.moviecompose.ui.theme.MovieComposeTheme

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : ComponentActivity() {
    private lateinit var user: UserModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
        loginViewModel.getUser().observe(this) { user ->
            this.user = user
            Log.d(TAG, "onCreate: ${user.email}")

        }
        setContent {
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Login(loginViewModel, this)
                }
            }
        }
    }

}


@Composable
fun Login(loginViewModel: LoginViewModel, loginActivity: ComponentActivity) {
    val context = LocalContext.current
    var email = remember { mutableStateOf(TextFieldValue()) }
    var password = remember { mutableStateOf(TextFieldValue()) }

    var visibilityPassword: Boolean by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(vertical = 16.dp)
                .padding(top = 24.dp),
        )
        Text(
            "Login",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailError){
                    emailError = false
                }
                email.value = it },
            label = {
                if (emailError)
                    Text(text = "Email is required", color = Color.Red)
                else
                    Text(text = "Email", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "Email Icon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordError){
                    passwordError = false
                }
                password.value = it },
            label = {
                if (passwordError)
                    Text(text = "Password is required", color = Color.Red)
                else
                    Text(text = "Password", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = "Pass Icon")

            },
            visualTransformation = if (visibilityPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Button(
            onClick = {
                when {
                    email.value.text.isEmpty() -> {
                        emailError = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordError = true
                    }
                    else -> {
                        val intent = Intent(context.applicationContext, MainActivity2::class.java)
                        context.startActivity(intent)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, bottom = 25.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentPadding = PaddingValues(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
            )
        ) {
            Text("Login", color = Color.White)
        }

        Row() {
            Text(
                text = stringResource(id = R.string.text_question_regist),
                color = colorResource(id = R.color.black)
            )

            Text(
                text = " ${stringResource(id = R.string.daftar)}",
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    val intent = Intent(context.applicationContext, RegisterActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieComposeTheme {

    }
}