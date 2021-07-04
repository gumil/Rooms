package dev.gumil.rooms.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.di.AppContainer
import dev.gumil.rooms.ui.theme.RoomsTheme
import kotlin.random.Random

@Composable
fun RoomsListScreen(appContainer: AppContainer) {
    val (result, onEvent) = produceUiState(appContainer = appContainer)

    RoomsListScreen(result.value, onEvent)
}

@Composable
private fun RoomsListScreen(
    state: UiState<List<Room>>,
    onEvent: (Event) -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.loading),
        onRefresh = { onEvent(Event.Refresh) }
    ) {
        state.data?.let { rooms ->
            RoomsListScreen(rooms = rooms)
        }
    }
}

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
