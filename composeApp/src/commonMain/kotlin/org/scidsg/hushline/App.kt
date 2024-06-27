package org.scidsg.hushline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.scidsg.hushline.ui.home.HomeScreen
import org.scidsg.hushline.ui.landing.LoginScreen
import org.scidsg.hushline.ui.landing.LoginViewModel

@Composable
@Preview
fun App() {
    AppContent()
}

@Composable
fun AppContent(loginViewModel: LoginViewModel = koinInject<LoginViewModel>()) {
    var loggedIn by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        loggedIn = loginViewModel.isUserLoggedIn()
        isLoading = false
    }

    KoinContext {
        MaterialTheme {
            //TODO show a loading screen while waiting
            if (loggedIn) {
                Navigator(HomeScreen()) {
                    SlideTransition(it)
                }
            } else {
                Navigator(LoginScreen()) {
                    SlideTransition(it)
                }
            }

            // Full-screen loading indicator
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000)), // Semi-transparent black background
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 4.dp
                    )
                }
            }

            /*var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me now!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
            }*/
        }
    }
}