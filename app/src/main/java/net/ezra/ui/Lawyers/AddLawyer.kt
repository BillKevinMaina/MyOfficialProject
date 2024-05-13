package net.ezra.ui.AddLawyer


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import net.ezra.navigation.ROUTE_ADD_STUDENTS
import net.ezra.navigation.ROUTE_DOCUMENT
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LAWYER
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_VIEW_STUDENTS
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLawyer(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column ( modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    val context = LocalContext.current.applicationContext

                    TopAppBar(
                        title = {
                            Text(
                                text = "Kanga & Co. Advocates",
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {

                                navController.navigate(ROUTE_HOME) {
                                    popUpTo(ROUTE_DOCUMENT) { inclusive = true }
                                }

                            }) {
                                Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFFFFFFF),
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        ),
                        actions = {
                            IconButton(onClick = {

                                navController.navigate(ROUTE_LOGIN) {
                                    popUpTo(ROUTE_LAWYER) { inclusive = true }
                                }
                            }

                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Login",
                                    tint = Color(0xFF386FFA)
                                )
                            }


                            IconButton(onClick = {


                                navController.navigate(ROUTE_DOCUMENT) {
                                    popUpTo(ROUTE_LAWYER) { inclusive = true }
                                }



                            }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.List,
                                    contentDescription = "Add Client",
                                    tint = Color(0xFF386FFA)
                                )
                            }


                            IconButton(onClick = {


                                navController.navigate(ROUTE_ADD_STUDENTS) {
                                    popUpTo(ROUTE_LAWYER) { inclusive = true }
                                }

                            }


                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = "person",
                                    tint = Color(0xFF386FFA)
                                )





                            }
                        })
                }
                Image(
                    painter = painterResource(id = net.ezra.R.drawable.kangalogo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)

                )
            }
            Column(

                modifier = Modifier.padding(15.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text( "Lawyer Details", fontSize = 30.sp)

                var photoUri: Uri? by remember { mutableStateOf(null) }
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    photoUri = uri
                }

                var Name by rememberSaveable {
                    mutableStateOf("")
                }


                var email1 by rememberSaveable {
                    mutableStateOf("")
                }

                var phone1 by rememberSaveable {
                    mutableStateOf("")
                }

                var Qualifications by rememberSaveable {
                    mutableStateOf("")
                }



                OutlinedTextField(
                    value = Name,
                    onValueChange = { Name = it },
                    label = { Text(text = "Lawyer Name") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )



                OutlinedTextField(
                    value = email1,
                    onValueChange = { email1= it },
                    label = { Text(text = "Email") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone1,
                    onValueChange = { phone1= it },
                    label = { Text(text = "Business Number") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )




                OutlinedTextField(
                    value = Qualifications,
                    onValueChange = { Qualifications= it },
                    label = { Text(text = "Qualifications of the lawyer") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                )




                if (photoUri != null) {
                    //Use Coil to display the selected image
                    val painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = photoUri)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(150.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray),
                        contentScale = ContentScale.Crop,

                        )
                }


                OutlinedButton(onClick = {
                    photoUri?.let { uploadImageToFirebaseStorage(it, Name, email1, phone1, Qualifications) }


                },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFF386FFA)
                        ),

                ) {

                    Text(text = "Save Lawyer Details")


                }

                OutlinedButton(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text("Attach Image [REQUIRED]",
                            color = Color.Black
                        )
                }

            }

        }
    }
}










fun uploadImageToFirebaseStorage(imageUri: Uri, Name: String, email1:String, phone1:String, Qualifications:String) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("images/${UUID.randomUUID()}")

    val uploadTask = imageRef.putFile(imageUri)
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            saveToFirestore(downloadUri.toString(), Name, email1, phone1, Qualifications)
        } else {


        }
    }
}

fun saveToFirestore(imageUrl: String, Name: String, email1:String, phone1:String, Qualifications: String) {
    val db = Firebase.firestore
    val imageInfo = hashMapOf(
        "imageUrl" to imageUrl,
        "Name" to Name,
        "email1" to email1,
        "phone1" to phone1,
        "Qualifications" to Qualifications

    )




    db.collection("Lawyers")
        .add(imageInfo)
        .addOnSuccessListener {



        }
        .addOnFailureListener {
            // Handle error
        }
}







@Preview(showBackground = true)
@Composable
fun PreviewLight() {
    AddLawyer(rememberNavController())
    }