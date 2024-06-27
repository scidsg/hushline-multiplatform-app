package org.scidsg.hushline.ui.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import hushline.composeapp.generated.resources.Res
import hushline.composeapp.generated.resources.new_message_tab_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scidsg.hushline.getPlatform
import org.scidsg.hushline.ui.component.CommonSnackBarHost
import org.scidsg.hushline.ui.component.TopAppBarDonate
import org.scidsg.hushline.ui.theme.AppColor

class HomeScreen: Screen {

    private lateinit var snackbarHostState: SnackbarHostState
    private lateinit var navigator: Navigator

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        snackbarHostState = remember { SnackbarHostState() }
        navigator = LocalNavigator.currentOrThrow

        //dispose of the previous screen
        val screens = navigator.items
        val size = screens.size
        if (size > 1)
            navigator.dispose(screens[size - 2])

        if (getPlatform().name.contains("Java")) {
            DesktopHomeScreen()
        } else {
            MobileHomeScreen()
        }
    }

    @Preview
    @Composable
    fun MobileHomeScreen() {
        TabNavigator(
            InboxTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(InboxTab, NewMessageTab, SettingsTab)
                )
            }
        ) { tabNavigator ->
            Scaffold(
                topBar = { TopAppBarDonate(tabNavigator = tabNavigator) } /*{
                    TopAppBar(
                        title = { Text(text = tabNavigator.current.options.title) }
                    )
                }*/,
                //TODO: remove this snackbarhost as it is useless
                snackbarHost = {
                    CommonSnackBarHost(snackbarHostState)
                },
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = Color.White,
                        contentColor = AppColor
                    ) {
                        TabNavigationItem(InboxTab)
                        TabNavigationItem(NewMessageTab)
                        TabNavigationItem(SettingsTab)
                    }
                }
            )
        }
    }

    @Composable
    fun DesktopHomeScreen() {
    }

    @Preview
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = tab.options.title,
                    modifier = if (tab.options.title == stringResource(Res.string.new_message_tab_title))
                        Modifier.height(32.dp).width(32.dp) else Modifier.defaultMinSize(),
                    tint = if (tabNavigator.current.key == tab.key) AppColor else Color.Gray
                )
            },
            selectedContentColor = AppColor,
            unselectedContentColor = Color.Gray
        )
    }
}