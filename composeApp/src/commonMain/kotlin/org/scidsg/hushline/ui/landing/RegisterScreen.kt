package org.scidsg.hushline.ui.landing

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hushline.composeapp.generated.resources.Res
import hushline.composeapp.generated.resources.empty_login_details
import hushline.composeapp.generated.resources.internet_connection_unavailable
import hushline.composeapp.generated.resources.landing_login_instr
import hushline.composeapp.generated.resources.login_here
import hushline.composeapp.generated.resources.password
import hushline.composeapp.generated.resources.privacy_policy
import hushline.composeapp.generated.resources.register
import hushline.composeapp.generated.resources.register_failed
import hushline.composeapp.generated.resources.terms_of_service
import hushline.composeapp.generated.resources.username
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.scidsg.hushline.NetworkHelper
import org.scidsg.hushline.common.C.REGISTER_SCREEN_TAG
import org.scidsg.hushline.getPlatform
import org.scidsg.hushline.ui.component.CommonSnackBarHost
import org.scidsg.hushline.ui.component.LoadingScreen
import org.scidsg.hushline.ui.component.RoundedEdgeButton
import org.scidsg.hushline.ui.component.TopAppBarNoDonate
import org.scidsg.hushline.ui.theme.AppColor
import org.scidsg.hushline.ui.theme.AppSecondaryColor

class RegisterScreen: Screen {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var navigator: Navigator
    private lateinit var snackbarHostState: SnackbarHostState
    private lateinit var keyboardController: SoftwareKeyboardController
    private lateinit var networkHelper: NetworkHelper
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var isLoading: MutableState<Boolean>
    private lateinit var registerBtnEnabled: MutableState<Boolean>
    private lateinit var internetConnErrorMsg: String
    private lateinit var registerFailedErrorMsg: String
    private lateinit var emptyLoginDetailsMsg: String


    @Composable
    override fun Content() {
        navigator = LocalNavigator.currentOrThrow
        snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            topBar = {
                TopAppBarNoDonate(navigator = navigator, tag = REGISTER_SCREEN_TAG)
            },
            snackbarHost = {
                CommonSnackBarHost(snackbarHostState)
            }
        ) {
            RegisterScreenContent()
        }
    }

    @Composable
    fun RegisterScreenContent() {
        coroutineScope = rememberCoroutineScope()
        keyboardController = LocalSoftwareKeyboardController.currentOrThrow
        registerViewModel = getScreenModel<RegisterViewModel>()
        networkHelper = koinInject<NetworkHelper>()

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        isLoading = remember { mutableStateOf(false) }
        registerBtnEnabled = remember { mutableStateOf(false) }

        internetConnErrorMsg = stringResource(Res.string.internet_connection_unavailable)
        registerFailedErrorMsg = stringResource(Res.string.register_failed)
        emptyLoginDetailsMsg = stringResource(Res.string.empty_login_details)

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
                        text = stringResource(Res.string.register),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            registerBtnEnabled.value = password.isNotEmpty() && username.isNotEmpty()
                        },
                        label = { Text(stringResource(Res.string.username)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            registerBtnEnabled.value = password.isNotEmpty() && username.isNotEmpty()
                        },
                        label = { Text(stringResource(Res.string.password)) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        /*keyboardOptions = KeyboardOptions.Default.copy(
                              imeAction = ImeAction.Done
                        ) */
                        keyboardActions = KeyboardActions(onDone = {
                            processRegister(username, password, navigator)
                        })
                    )

                    /*AnimatedVisibility(visible = isLoading.value) {
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.height(24.dp).width(24.dp),
                            color = Color.LightGray
                            //backgroundColor = WhiteTranslucent
                        )
                    }*/

                    Spacer(modifier = Modifier.height(24.dp))

                    RoundedEdgeButton(
                        text = stringResource(Res.string.register),
                        enabled = registerBtnEnabled.value,
                        onClick = {
                            processRegister(username, password, navigator)
                        },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {
                        navigator.pop() //Note: deep-linking will have to mind this
                        //navigator.push(LoginScreen())
                    }) {
                        Text(
                            stringResource(Res.string.landing_login_instr),
                            color = Color.Black
                        )
                        Text(
                            stringResource(Res.string.login_here),
                            color = AppColor
                        )
                    }
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

    private fun processRegister(username: String, password: String, navigator: Navigator) {
        keyboardController.hide()
        if (networkHelper.isNetworkAvailable()) {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                isLoading.value = true
                registerBtnEnabled.value = false

                registerViewModel.register(username, password, {
                    isLoading.value = false
                    registerBtnEnabled.value = true
                    navigator.pop()
                    //TODO show success indicator
                }, {
                    isLoading.value = false
                    registerBtnEnabled.value = true
                    showSnackBar(message = registerFailedErrorMsg + it.message.toString())
                })
            } else {
                showSnackBar(message = emptyLoginDetailsMsg)
            }
        } else {
            isLoading.value =  false
            registerBtnEnabled.value = true
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