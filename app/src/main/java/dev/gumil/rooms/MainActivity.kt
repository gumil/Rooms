package dev.gumil.rooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.gumil.rooms.ui.theme.RoomsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomsTheme {
                RoomsApp()
            }
        }
    }
}

@Composable
fun RoomsApp() {
    RoomsTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = stringResource(id = R.string.app_name))
                })
            },
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    Text(text = "Hello World")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomsApp()
}
