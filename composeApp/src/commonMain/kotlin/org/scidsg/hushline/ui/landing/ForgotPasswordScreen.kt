package org.scidsg.hushline.ui.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hushline.composeapp.generated.resources.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.scidsg.hushline.NetworkHelper
import org.scidsg.hushline.common.C.FORGOT_PASSWORD_SCREEN_TAG
import org.scidsg.hushline.getPlatform
import org.scidsg.hushline.ui.component.CommonSnackBarHost
import org.scidsg.hushline.ui.component.LoadingScreen
import org.scidsg.hushline.ui.component.RoundedEdgeButton
import org.scidsg.hushline.ui.component.TopAppBarNoDonate
import org.scidsg.hushline.ui.theme.AppColor
import org.scidsg.hushline.ui.theme.AppSecondaryColor

class ForgotPasswordScreen: Screen {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var navigator: Navigator
    private lateinit var snackbarHostState: SnackbarHostState
    private lateinit var keyboardController: SoftwareKeyboardController
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    private lateinit var networkHelper: NetworkHelper

    private lateinit var isLoading: MutableState<Boolean>
    private lateinit var resetBtnEnabled: MutableState<Boolean>
    private lateinit var internetConnErrorMsg: String
    private lateinit var reqFailedErrorMsg: String
    private lateinit var invalidEmailMsg: String

    @Composable
    override fun Content() {
        navigator = LocalNavigator.currentOrThrow
        snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            topBar = {
                TopAppBarNoDonate(navigator = navigator, tag = FORGOT_PASSWORD_SCREEN_TAG)
            },
            snackbarHost = {
                CommonSnackBarHost(snackbarHostState)
            }
        ) {
            ForgotPasswordScreenContent()
        }
    }

    @Composable
    fun ForgotPasswordScreenContent() {
        coroutineScope = rememberCoroutineScope()
        keyboardController = LocalSoftwareKeyboardController.currentOrThrow
        forgotPasswordViewModel = getScreenModel<ForgotPasswordViewModel>()
        networkHelper = koinInject<NetworkHelper>()

        var emailAddress by remember { mutableStateOf("") }
        isLoading = remember { mutableStateOf(false) }
        resetBtnEnabled = remember { mutableStateOf(false) }

        internetConnErrorMsg = stringResource(Res.string.internet_connection_unavailable)
        reqFailedErrorMsg = stringResource(Res.string.request_failed)
        invalidEmailMsg = stringResource(Res.string.invalid_email)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppSecondaryColor)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(if (getPlatform().name.contains("Java")) 0.5f else 1.0f),
                elevation = 2.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.reset_password),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(Res.string.reset_password_instr),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = emailAddress,
                        onValueChange = {
                            emailAddress = it
                            resetBtnEnabled.value = emailAddress.isNotEmpty()
                        },
                        label = { Text(stringResource(Res.string.email_address)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    /*AnimatedVisibility(visible = isLoading.value) {
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.height(24.dp).width(24.dp),
                            color = Color.LightGray
                            //backgroundColor = WhiteTranslucent
                        )
                    }*/

                    Spacer(modifier = Modifier.height(48.dp))

                    RoundedEdgeButton(
                        text = stringResource(Res.string.reset),
                        enabled = resetBtnEnabled.value,
                        onClick = {
                            processReset(emailAddress)
                        },
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(Res.string.privacy_policy),
                    color = AppColor,
                    modifier = Modifier.clickable { /* TODO */ }
                )
                Text(text = " | ", color = AppColor)
                Text(
                    text = stringResource(Res.string.terms_of_service),
                    color = AppColor,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
        }

        if (isLoading.value) {
            LoadingScreen()
        }
    }

    private fun processReset(emailAddress: String) {
        keyboardController.hide()
        if (networkHelper.isNetworkAvailable()) {
            if (emailAddress.isNotEmpty() && forgotPasswordViewModel.isValidEmailAddress(emailAddress)) {
                isLoading.value = true
                resetBtnEnabled.value = false

                forgotPasswordViewModel.sendResetLink(emailAddress, {
                    isLoading.value = false
                    resetBtnEnabled.value = true
                    navigator.pop()
                    //TODO: indicate success
                }, {
                    isLoading.value = false
                    resetBtnEnabled.value = true
                    showSnackBar(message = reqFailedErrorMsg + it.message.toString())
                })
            } else {
                showSnackBar(message = invalidEmailMsg)
            }
        } else {
            isLoading.value = false
            resetBtnEnabled.value = true
            showSnackBar(message = internetConnErrorMsg)
        }
    }

    private fun showSnackBar(message: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }
}