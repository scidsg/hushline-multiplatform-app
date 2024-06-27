package org.scidsg.hushline.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import hushline.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.scidsg.hushline.common.C.FORGOT_PASSWORD_SCREEN_TAG
import org.scidsg.hushline.ui.theme.AppColor
import org.scidsg.hushline.ui.theme.BlackTranslucent

@Composable
fun RoundedEdgeButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 200.dp, height = 50.dp),
        enabled = enabled,
        shape = RoundedCornerShape(50), // Very rounded corners. Adjust the percentage for preference
        colors = ButtonDefaults.buttonColors(backgroundColor = AppColor)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RoundedEdgeButtonOutline(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 90.dp, height = 30.dp).padding(end = 4.dp),
        enabled = enabled,
        shape = RoundedCornerShape(50), // Very rounded corners. Adjust the percentage for preference
        border = BorderStroke(
            width = 1.dp,
            color = AppColor
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        )
    ) {
        Text(
            text = text,
            color = AppColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TopAppBarNoDonate(navigator: Navigator, tag: String) {
    if (tag == FORGOT_PASSWORD_SCREEN_TAG) {
        TopAppBarNoDonateBack(navigator = navigator)
    } else {
        TopAppBarNoDonateNoBack()
    }
}

@Composable
private fun TopAppBarNoDonateBack(navigator: Navigator) {
    TopAppBar(
        title = {
            LogoTitle()
        },
        navigationIcon = {
            IconButton(
                onClick = { navigator.pop() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        backgroundColor = Color.White,
        elevation = 4.dp
    )
}

@Composable
private fun TopAppBarNoDonateNoBack() {
    TopAppBar(
        title = {
            LogoTitle()
        },
        backgroundColor = Color.White,
        elevation = 4.dp
    )
}

@Composable
private fun LogoTitle() {
    Image(
        painter = painterResource(Res.drawable.compose_multiplatform),
        contentDescription = stringResource(Res.string.logo),
        modifier = Modifier.size(30.dp),
        alignment = Alignment.CenterStart
    )
    Text(
        text = stringResource(Res.string.app_name),
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun TopAppBarDonate(tabNavigator: TabNavigator? = null, navigator: Navigator? = null) {
    TopAppBar(
        title = {
            LogoTitle()
        },
        /*navigationIcon = {
            IconButton(onClick = { /* TODO: Handle menu click */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },*/
        actions = {
            RoundedEdgeButtonOutline(
                text = stringResource(Res.string.donate),
                enabled = true,
                onClick = { /* TODO */ }
            )
        },
        backgroundColor = Color.White,
        elevation = 4.dp
    )
}

@Composable
fun CommonSnackBarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.fillMaxWidth(),
        snackbar = {
            Snackbar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Red,
                contentColor = Color.White,
                content = { Text(text = it.visuals.message, color = Color.White) }
            )
        }
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackTranslucent), //Semi-transparent black background
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 4.dp
        )
    }
}