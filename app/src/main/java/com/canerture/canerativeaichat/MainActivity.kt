package com.canerture.canerativeaichat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.canerture.canerativeaichat.presentation.profile.ProfileScreen
import com.canerture.canerativeaichat.presentation.sign_in.GoogleAuthUiClient
import com.canerture.canerativeaichat.presentation.sign_in.SignInState
import com.canerture.canerativeaichat.presentation.sign_in.SignInViewModel
import com.canerture.canerativeaichat.ui.theme.CanerativeAIChatTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
        setContent {
            CanerativeAIChatTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }

                            val launcher =
                                rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult()
                                ) { result: ActivityResult ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult =
                                                googleAuthUiClient.signInWithIntent(
                                                    intent = result.data
                                                        ?: return@launch
                                                )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("profile")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender =
                                            googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }

                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @Composable
    fun SignInScreen(
        state: SignInState, // Assuming SignInState is your state class
        onSignInClick: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .size(width = 300.dp, height = 50.dp), // Adjust width and height as needed
                contentPadding = PaddingValues(0.dp) // Remove content padding
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_100), // Replace with your image resource
                        contentDescription = "Image",
                        modifier = Modifier.size(80.dp) // Adjust image size as needed
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign In")
                }

            }
        }
    }
}
