package dev.gumil.rooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.gumil.rooms.di.AppContainer
import dev.gumil.rooms.ui.RoomsListScreen
import dev.gumil.rooms.ui.theme.RoomsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as RoomsApplication).container
        setContent {
            RoomsTheme {
                RoomsApp(appContainer)
            }
        }
    }

    @Composable
    private fun RoomsApp(appContainer: AppContainer) {
        RoomsTheme {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(title = {
                        Text(text = stringResource(id = R.string.app_name))
                    })
                },
                content = {
                    RoomsListScreen(
                        appContainer = appContainer,
                        scaffoldState = scaffoldState
                    )
                }
            )
        }
    }
}
