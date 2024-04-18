package com.canerture.canerativeaichat.presentation.profile

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.canerture.canerativeaichat.HomePageActivity
import com.canerture.canerativeaichat.presentation.sign_in.UserData


@Composable
fun ProfileScreen(

    userData: UserData?,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") {
            Screen1(navController = navController)
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(userData?.profilePictureUrl != null) {
            Spacer(modifier = Modifier.height(75.dp))
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
        if(userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(25.dp))
        }

        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }
    Spacer(modifier = Modifier.height(50.dp))
}

@Composable
fun Screen1(navController: NavController) {
    Spacer(modifier = Modifier.height(50.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            // Start HomePageActivity using an intent
            val intent = Intent(navController.context, HomePageActivity::class.java)
            navController.context.startActivity(intent)
        },modifier = Modifier.size(width = 200.dp, height = 50.dp)
        ) {
            Text("Jamban-Gemini")
        }
    }
}


