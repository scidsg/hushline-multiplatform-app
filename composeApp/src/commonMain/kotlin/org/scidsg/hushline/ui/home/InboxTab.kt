package org.scidsg.hushline.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import hushline.composeapp.generated.resources.Res
import hushline.composeapp.generated.resources.compose_multiplatform
import hushline.composeapp.generated.resources.inbox_tab_title
import hushline.composeapp.generated.resources.internet_connection_unavailable
import hushline.composeapp.generated.resources.logo
import hushline.composeapp.generated.resources.no_messages_yet
import hushline.composeapp.generated.resources.nothing_to_see_here
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.scidsg.hushline.NetworkHelper
import org.scidsg.hushline.getPlatform
import org.scidsg.hushline.ui.component.CommonSnackBarHost
import org.scidsg.hushline.ui.theme.AppSecondaryColor

object InboxTab : Tab {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var snackbarHostState: SnackbarHostState
    private lateinit var networkHelper: NetworkHelper
    private lateinit var inboxTabViewModel: InboxTabViewModel

    private lateinit var isLoading: MutableState<Boolean>
    private lateinit var internetConnErrorMsg: String

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.inbox_tab_title)
            val icon = rememberVectorPainter(Icons.Default.Email)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        snackbarHostState = remember { SnackbarHostState() }
        isLoading = remember { mutableStateOf(true) }
        internetConnErrorMsg = stringResource(Res.string.internet_connection_unavailable)
        coroutineScope = rememberCoroutineScope()
        networkHelper = koinInject<NetworkHelper>()

        var categorizedMessages by remember { mutableStateOf(categorizeMessages(messages)) }

        LaunchedEffect(true) { //TODO refresh every now and then
            categorizedMessages = inboxTabViewModel.getMessages().collect()
        }

        Scaffold(snackbarHost = {
            CommonSnackBarHost(snackbarHostState)
        }) {
            EmptyInboxView()
        }
    }

    @Composable
    private fun EmptyInboxView() {
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
                    .padding(
                        top = 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 70.dp
                    )
                    .fillMaxWidth(if (getPlatform().name.contains("Java")) 0.5f else 1.0f)
                    .fillMaxHeight(),
                elevation = 2.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {

                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = stringResource(Res.string.logo),
                        modifier = Modifier.size(100.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(Res.string.nothing_to_see_here),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(Res.string.no_messages_yet),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    @Composable
    private fun MessageListView() {
        Column {
            LazyColumn {

            }
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

    private fun categorizeMessages(messages: List<Message>): Map<String, List<Message>> {
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        val oneDayMillis = 24 * 60 * 60 * 1000
        val oneWeekMillis = 7 * oneDayMillis

        return messages.groupBy {
            when {
                now - it.timestamp < oneDayMillis -> "Today"
                now - it.timestamp < 2 * oneDayMillis -> "Yesterday"
                now - it.timestamp < oneWeekMillis -> "Last Week"
                calendar.apply { timeInMillis = it.timestamp }.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) -> "This Month"
                else -> "Older"
            }
        }
    }
}