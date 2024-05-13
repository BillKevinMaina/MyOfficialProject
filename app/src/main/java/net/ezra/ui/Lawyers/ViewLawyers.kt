package net.ezra.ui.Lawyers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import androidx.lifecycle.ViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_VIEW_LAWYER
import net.ezra.ui.students.FirestoreViewModel


data class Item(

    val imageUrl: String? = "",
    val Name: String? = "",
    val email1: String? = "",
    val phone1: String? = "",
    val Qualifications: String? = "",

)


class FirestoreViewModel : ViewModel() {

    private val firestore = Firebase.firestore
    private val itemsCollection = firestore.collection("Lawyers")

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        fetchItems()
    }

    fun fetchItems() {
        itemsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FirestoreViewModel", "Error fetching items", error)
                return@addSnapshotListener
            }

            val itemList = mutableListOf<Item>()
            snapshot?.documents?.forEach { document ->
                val item = document.toObject(Item::class.java)?.copy(Name = document.id)
                item?.let {
                    itemList.add(it)
                }
            }
            _items.value = itemList
        }
    }
}



@Composable
fun ItemLists(items: List<Item>) {

    Column {



        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(10.dp)
        ) {

            items.forEach { item ->
                item {
                    Column {


                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.imageUrl)
                                .crossfade(true)
                                .build(),
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = item.Name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(RoundedCornerShape(10))
                        )

                        item.Name?.let { Text(text = it) }
                        item.phone1?.let { Text(text = it) }
                        item.email1?.let { Text(text = it) }
                        item.Qualifications?.let { Text(text = it) }


                    }

                }
            }


        }





    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawyerList(navController: NavHostController, viewModel: FirestoreViewModel) {
    val items by viewModel.items.observeAsState(initial = emptyList())

    // Fetch items when the composable is first created
    LaunchedEffect(viewModel) {
        viewModel.fetchItems()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Lawyers and Advocates")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_VIEW_LAWYER) { inclusive = true }
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.White
                        )
                    }
                },


                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFBEEBFF),


                    titleContentColor = Color.White,
                ),
            )
        },

        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color(0xFFFF8B8B)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                net.ezra.ui.students.StudentList(items)


            }
        },


        )




}