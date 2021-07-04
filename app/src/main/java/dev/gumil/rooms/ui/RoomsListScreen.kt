package dev.gumil.rooms.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        val rooms = state.data

        when {
            rooms != null && rooms.isNotEmpty() -> {
                RoomsListScreen(rooms = rooms) { room ->
                    onEvent(Event.Book(room))
                }
            }
            !state.loading -> {
                EmptyScreen()
            }
        }
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.rooms_not_found),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
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

@Preview
@Composable
fun PreviewEmptyScreen() {
    RoomsTheme {
        EmptyScreen()
    }
}
