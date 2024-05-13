package net.ezra.ui.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_SIGNUP




@Composable
fun SignupScreen(navController: NavController, onSignupSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current



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
                Spacer(modifier = Modifier.height(27.dp))


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
                Spacer(modifier = Modifier.height(27.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
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
                Spacer(modifier = Modifier.height(27.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                } else {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF8B8B)),
                        onClick = {
                            if (email.isBlank()){
                                error = "Email is required"
                            } else if (password.isBlank()){
                                error = "Password is required"
                            } else if(confirmPassword.isBlank()) {
                                error = "Password Confirmation required"
                            } else if (password != confirmPassword) {
                                error = "Passwords do not match"
                            } else {
                                isLoading = true
                                signup(email, password, {
                                    isLoading = false
                                    Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                                    onSignupSuccess()
                                }) { errorMessage ->
                                    isLoading = false
                                    error = errorMessage
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    androidx.compose.material3.Text(
                        modifier = Modifier

                            .clickable {
                                navController.navigate(ROUTE_LOGIN) {
                                    popUpTo(ROUTE_SIGNUP) { inclusive = true }
                                }
                            },
                        text = "Already have an account?",
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
private fun signup(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods ?: emptyList()
                if (signInMethods.isNotEmpty()) {
                    onError("Email is already registered")
                } else {
                    // Email is not registered, proceed with sign-up
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { signUpTask ->
                            if (signUpTask.isSuccessful) {
                                onSuccess()
                            } else {
                                onError(signUpTask.exception?.message ?: "Sign-up failed")
                            }
                        }
                }
            } else {
                onError(task.exception?.message ?: "Failed to check email availability")
            }
        }
}

