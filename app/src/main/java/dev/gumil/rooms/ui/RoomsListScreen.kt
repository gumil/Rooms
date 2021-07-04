package dev.gumil.rooms.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.gumil.rooms.R
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.di.AppContainer
import dev.gumil.rooms.ui.theme.RoomsTheme
import kotlin.random.Random

@Composable
fun RoomsListScreen(
    appContainer: AppContainer,
    scaffoldState: ScaffoldState
) {
    val (result, onEvent) = produceUiState(appContainer = appContainer)

    RoomsListScreen(result.value, onEvent, scaffoldState)
}

@Composable
private fun RoomsListScreen(
    state: UiState<List<Room>>,
    onEvent: (Event) -> Unit,
    scaffoldState: ScaffoldState
) {
    SnackbarOnException(state, scaffoldState, onEvent)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.loading),
        onRefresh = { onEvent(Event.Refresh) }
    ) {
        state.data?.let { rooms ->
            RoomsListScreen(rooms = rooms) { room ->
                onEvent(Event.Book(room))
            }
        }
    }
}

@Composable
private fun SnackbarOnException(
    state: UiState<List<Room>>,
    scaffoldState: ScaffoldState,
    onEvent: (Event) -> Unit
) {
    state.exception?.let { exception ->
        val message = stringResource(id = R.string.error)
        val refresh = stringResource(id = R.string.refresh)
        LaunchedEffect(scaffoldState.snackbarHostState) {
            when (exception) {
                is RoomsException.BookingException -> {
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }
                is RoomsException.LoadingException -> {
                    val result = scaffoldState.snackbarHostState
                        .showSnackbar(message, refresh, SnackbarDuration.Long)
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(Event.Refresh)
                    }
                }
            }

        }
    }
}

@Composable
fun RoomsListScreen(
    rooms: List<Room>,
    onBookRoom: (Room) -> Unit
) {
    LazyColumn {
        item {
            rooms.forEach { room ->
                RoomsListItem(
                    room = room,
                    onBookRoom = onBookRoom
                )
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
        RoomsListScreen(rooms = rooms) {}
    }
}
