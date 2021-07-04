package dev.gumil.rooms.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.repository.RoomsRepository
import dev.gumil.rooms.di.AppContainer
import dev.gumil.rooms.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomsListViewModel(
    private val repository: RoomsRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val state: MutableState<UiState<List<Room>>>,
    private val scope: CoroutineScope
) {
    fun loadRooms() {
        scope.launch {
            state.value = state.value.copy(loading = true)
            runCatching {
                withContext(dispatcherProvider.io) {
                    repository.getRooms()
                }
            }.onFailure { throwable ->
                state.value = UiState(exception = RuntimeException(throwable))
            }.onSuccess { rooms ->
                state.value = UiState(data = rooms)
            }
        }
    }
}

sealed class Event {
    object Refresh: Event()
}

data class UiState<T>(
    val loading: Boolean = false,
    val exception: Exception? = null,
    val data: T? = null
)

data class ProducerResult<T>(
    val result: State<T>,
    val onEvent: (Event) -> Unit
)

@Composable
fun produceUiState(
    appContainer: AppContainer
): ProducerResult<UiState<List<Room>>> {
    val result = remember { mutableStateOf(UiState<List<Room>>(loading = true)) }
    val channel = remember { Channel<Event>(Channel.CONFLATED) }
    LaunchedEffect(Unit) {
        val viewModel = RoomsListViewModel(
            appContainer.roomsRepository,
            appContainer.dispatcherProvider,
            state = result,
            scope = this
        )

        channel.send(Event.Refresh)

        for (event in channel) {
            when (event) {
                Event.Refresh -> viewModel.loadRooms()
            }
        }
    }
    return ProducerResult(result) { event ->
        channel.trySend(event)
    }
}
