package net.ezra.ui.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_SIGNUP

@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    BackHandler {
        navController.popBackStack()

    }

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                AuthHeader()

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedLabelColor = Color(0xFFFFB3B3),
                        unfocusedIndicatorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color(0xFFA7D1F1)
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedLabelColor = Color(0xFFFFB3B3),
                        unfocusedIndicatorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color(0xFFA7D1F1)
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                } else {



                    Button(
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF8B8B)),
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                error = "Please fill in all fields"
                            } else {
                                isLoading = true
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        isLoading = false
                                        if (task.isSuccessful) {
                                            navController.navigate(ROUTE_HOME)
                                        } else {
                                            error = task.exception?.message ?: "Login failed"
                                        }
                                    }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    androidx.compose.material3.Text(
                        modifier = Modifier

                            .clickable {
                                navController.navigate(ROUTE_SIGNUP) {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            },
                        text = "Click here to SignUp",
                        textAlign = TextAlign.Center,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )



                }

                error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}