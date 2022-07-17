package com.example.moviecompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
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
import com.example.moviecompose.model.UserModel
import com.example.moviecompose.model.UserPreferences
import com.example.moviecompose.register.RegisterViewModel
import com.example.moviecompose.ui.theme.MovieComposeTheme

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val registerViewModel = RegisterViewModel(pref = UserPreferences.getInstance(dataStore))
        setContent {
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Register(registerViewModel)
                }
            }
        }
    }
}



@Composable
fun Register(registerViewModel: RegisterViewModel) {
    val context = LocalContext.current
    var username by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    var visibilityPassword: Boolean by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
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
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(vertical = 16.dp)
                .padding(top = 24.dp),
        )
        Text(
            "Sign Up",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                if (usernameError){
                    usernameError = false
                }
                username = it },
            label = {
                if (usernameError)
                    Text(text = "Username is required", color = Color.Red)
                else
                    Text(text = "Username", color = Color.LightGray)
                    },
            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = "Username Icon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )


        OutlinedTextField(
            value = email,
            onValueChange = {
                if (emailError){
                    emailError = false
                }
                email = it },
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
            value = password,
            onValueChange = {
                if (passwordError){
                    passwordError = false
                }
                    password = it },
            label = {
                if (passwordError)
                    Text(text = "Password is required", color = Color.Red)
                else
                    Text(text = "Password", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = "Pass Icon") 
//                          IconButton(onClick = { visibilityPassword = !visibilityPassword }) {
//                              Icon(imageVector = Icons.Default.Check, contentDescription = "icon")
//                            }
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
                          username.text.isEmpty() -> {
                              usernameError = true
                          }
                          email.text.isEmpty() -> {
                              emailError = true
                          }
                          password.text.isEmpty() -> {
                              passwordError = true
                          }
                          else -> {
                              registerViewModel.saveUser(UserModel(username.text, email.text, password.text, false))
                              val intent = Intent(context.applicationContext, LoginActivity::class.java)
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
            Text("Register", color = Color.White)
        }
    }
}

