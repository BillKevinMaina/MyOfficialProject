package net.ezra.ui


import android.content.res.Configuration
import android.graphics.Paint.Align
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import net.ezra.R
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_SIGNUP


@Composable
fun SplashScreen(navController: NavHostController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        // Customize the delay time
        delay(3L)
        navController.navigate(ROUTE_SIGNUP)
    }

    // Image

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(painter = painterResource(id = R.drawable.kangalogo),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(),
        )





    }
    Row(

        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom

    ) {
        Text(text = "App Is Configuring...Please wait",
            fontSize = 15.sp,
            color = Color.DarkGray

        )
    }



}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    SplashScreen(rememberNavController())
}

