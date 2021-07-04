package dev.gumil.rooms.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.ui.theme.RoomsTheme
import kotlin.random.Random

@Composable
fun RoomsListScreen(rooms: List<Room>) {
    LazyColumn {
        item {
            rooms.forEach { room ->
                RoomsListItem(room = room)
            }
        }
    }
}

@Preview
@Composable
fun PreviewRoomsListScreen() {
    val rooms = (0..5).map {
        Room(
            name = "Ljerka $it",
            spots = Random.nextInt(99),
            thumbnail = ""
        )
    }
    RoomsTheme {
        RoomsListScreen(rooms = rooms)
    }
}
