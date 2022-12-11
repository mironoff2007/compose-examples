package ru.mironov.compose_examples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.mironov.compose_examples.ui.AccountViewModel
import ru.mironov.compose_examples.ui.HelpViewModel
import ru.mironov.compose_examples.ui.HomeViewModel
import ru.mironov.compose_examples.ui.navigation.Drawer
import ru.mironov.compose_examples.ui.navigation.DrawerScreens
import ru.mironov.compose_examples.ui.navigation.NavigationTree
import ru.mironov.compose_examples.ui.navigation.TopBar
import ru.mironov.compose_examples.ui.theme.ComposeexamplesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeexamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    ModalDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = drawerState.isOpen,
                        drawerContent = {
                            Drawer(
                                onDestinationClicked = { route ->
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(route) {
                                        popUpTo = navController.graph.startDestinationId
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationTree.Root.Home.name
                        ) {
                            composable(DrawerScreens.Home.route) {
                                val viewModel = hiltViewModel<HomeViewModel>()
                                Home(
                                    viewModel = viewModel,
                                    openDrawer = { openDrawer() },
                                    navController = navController
                                )
                            }

                            composable(NavigationTree.Root.Account.name) {
                                val viewModel = hiltViewModel<AccountViewModel>()
                                Account(
                                    openDrawer = { openDrawer() },
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable(NavigationTree.Root.Help.name) {
                                val viewModel = hiltViewModel<HelpViewModel>()
                                Help(
                                    openDrawer = { openDrawer() },
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Home(openDrawer: () -> Unit, viewModel: HomeViewModel, navController: NavHostController) {
    TopBar(
        title = "Home",
        buttonIcon = Icons.Filled.Menu,
        onButtonClicked = { openDrawer() }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val flag by viewModel.flag.observeAsState()

        viewModel.event.Observe()
        viewModel.event.onEvent {
            when (it) {
                is HomeViewModel.Event.ButtonEvent -> {
                    viewModel.log( "Button click event")
                }
            }
        }

        viewModel.log( "toggle - $flag")

        Text(text = "Home", fontSize = 40.sp)
        Button(onClick = {
            viewModel.event.postEvent(HomeViewModel.Event.ButtonEvent)
            viewModel.toggle()
            viewModel.toggleAfterDelay(500)
        }) {
            Text(text = "click event", fontSize = 40.sp)
        }
    }

}

@Composable
fun Help(openDrawer: () -> Unit, viewModel: HelpViewModel, navController: NavHostController) {
    TopBar(
        title = "Help",
        buttonIcon = Icons.Filled.Info,
        onButtonClicked = { openDrawer() }
    )
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Help", fontSize = 40.sp)
    }
}

@Composable
fun Account(openDrawer: () -> Unit, viewModel: AccountViewModel, navController: NavHostController) {
    TopBar(
        title = "Account",
        buttonIcon = Icons.Filled.AccountCircle,
        onButtonClicked = { openDrawer() }
    )
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Account", fontSize = 40.sp)
    }
}
