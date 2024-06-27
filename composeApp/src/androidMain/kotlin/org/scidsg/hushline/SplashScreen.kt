package org.scidsg.hushline

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import hushline.composeapp.generated.resources.Res
import hushline.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    val alpha by animateFloatAsState(targetValue = 1f, animationSpec = tween(durationMillis = 3000))
    SplashContent(alpha = alpha)

    // Launching effect for initial setup or delay
    LaunchedEffect(key1 = true) {
        delay(3000)
        onAnimationFinished()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashContent(alpha: Float) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp).alpha(alpha)
        )
    }
}