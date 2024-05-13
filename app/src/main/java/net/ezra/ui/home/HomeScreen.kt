package net.ezra.ui.home


import android.content.ClipData.Item
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
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
import net.ezra.navigation.ROUTE_ABOUT
import net.ezra.navigation.ROUTE_ADD_STUDENTS

import net.ezra.navigation.ROUTE_DOCUMENT
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LAWYER
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_SIGNUP
import net.ezra.navigation.ROUTE_VIEW_STUDENTS
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    val context = LocalContext.current.applicationContext

//                    START OF TOP APP BAR
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
                                    popUpTo(ROUTE_ADD_STUDENTS) { inclusive = true }
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
                                            popUpTo(ROUTE_HOME) { inclusive = true }
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
                                            popUpTo(ROUTE_HOME) { inclusive = true }
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
                                            popUpTo(ROUTE_HOME) { inclusive = true }
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
//            END OF TOP APP BAR


            Column(

                modifier = Modifier.padding(15.dp),

//                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Card(
                    modifier = Modifier
                        .width(430.dp)
                        .height(200.dp)
                        .padding(2.dp)

                        .clickable {
                            navController.navigate(ROUTE_ABOUT) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(Color(0xE8A1E0FF))


                ) {
                    Text(
                        text = "As a law firm in Kenya with over 10 years of experience, our team at Kanga & Co. Advocates is dedicated to providing our clients with the highest quality legal services. We strive to achieve a fair and equitable resolution for each and every one of our clients, and we accomplish this through the diligence and dedication of our attorneys, who work tirelessly within the confines of the law.\n" +
                                "\n" +
                                "To Know More About Us Click Here",
                        fontStyle = FontStyle.Italic,
                        fontSize = 13.5.sp,
                        modifier = Modifier
                            .padding(3.dp)


                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Client",
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(2.dp))


                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(200.dp), border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {

                    Row {
                        Column {
                            Image(
                                painter = painterResource(id = net.ezra.R.drawable.img),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .height(290.dp)
                                    .width(130.dp)

                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Column(

                            modifier = Modifier
                                .padding(6.dp)

                        ) {
                            Text(
                                text = "'WE LISTEN WE LITIGATE YOU WIN'",
                                fontSize = 12.5.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(40.dp)
                                    .clickable {
                                        navController.navigate(ROUTE_ADD_STUDENTS) {
                                            popUpTo(ROUTE_HOME) { inclusive = true }
                                        }
                                    },
                                colors = CardDefaults.cardColors(Color.White),
                                border = BorderStroke(1.dp, Color(0xE8A1E0FF))
                            ) {
                                Text(
                                    text = "I'm a New Client",
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(40.dp)
                                    .clickable {
                                        navController.navigate(ROUTE_VIEW_STUDENTS) {
                                            popUpTo(ROUTE_VIEW_STUDENTS) { inclusive = true }
                                        }
                                    },
                                colors = CardDefaults.cardColors(Color.White),
                                border = BorderStroke(1.dp, Color(0xE8A1E0FF))
                            ) {
                                Text(
                                    text = "View other Clients ",
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }


                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),


                    ) {
                    Text(
                        text = "Lawyers and Advocates",
                        fontSize = 15.sp,
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.width(190.dp))

                }

                Spacer(modifier = Modifier.height(2.dp))


                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(190.dp)
                        .clickable {
                            navController.navigate(ROUTE_LAWYER) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        }, border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {

                    Row {
                        Column {
                            Image(
                                painter = painterResource(id = net.ezra.R.drawable.img_2),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .height(310.dp)
                                    .width(130.dp)


                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Column(

                            modifier = Modifier
                                .padding(6.dp)

                        ) {
                            Text(
                                text = "'I'M HERE TO SERVE YOU'",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(40.dp)
                                    .clickable {
                                        navController.navigate(ROUTE_LAWYER) {
                                            popUpTo(ROUTE_HOME) { inclusive = true }
                                        }
                                    },
                                colors = CardDefaults.cardColors(Color.White),
                                border = BorderStroke(1.dp, Color(0xE8A1E0FF))
                            ) {
                                Text(
                                    text = "Add a New Lawyer",
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(40.dp)
                                    .clickable {
                                        navController.navigate(ROUTE_VIEW_STUDENTS) {
                                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                                        }
                                    },
                                colors = CardDefaults.cardColors(Color.White),
                                border = BorderStroke(1.dp, Color(0xE8A1E0FF))
                            ) {
                                Text(
                                    text = "View All Lawyers",
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }


                        }
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Our Services",
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(2.dp))
                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(200.dp)
                        .padding(10.dp)
                        .clickable {
                            navController.navigate(ROUTE_DOCUMENT) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        }, border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),

                        horizontalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = painterResource(id = net.ezra.R.drawable.amg),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(290.dp)
                                .width(210.dp)

                        )

                        Spacer(modifier = Modifier.width(2.dp))
                        Row(

                            modifier = Modifier
                                .padding(11.dp)

                        ) {


                        }
                    }

                }







            }

        }
    }


}

@Preview(showBackground = true,)
@Composable
fun PreviewLight() {
    HomeScreen(rememberNavController())
}