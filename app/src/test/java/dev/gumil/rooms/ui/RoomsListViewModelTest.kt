package dev.gumil.rooms.ui

import androidx.compose.runtime.mutableStateOf
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.repository.RoomsRepository
import dev.gumil.rooms.util.TestDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class RoomsListViewModelTest {
    private val dispatcherProvider = TestDispatcherProvider()
    private val roomsRepository = mock<RoomsRepository>()
    private val state = mutableStateOf(UiState<List<Room>>())
    private val viewModelFactory = { scope: CoroutineScope ->
        RoomsListViewModel(roomsRepository, dispatcherProvider, state, scope)
    }

    @Test
    fun `load rooms success updates state data`() = runBlockingTest {
        val viewModel = viewModelFactory(this)

        val rooms = listOf(
            Room(
              name = "${Random.nextInt()}",
              spots = Random.nextInt(),
              thumbnail = "${Random.nextInt()}"
            )
        )

        whenever(roomsRepository.getRooms()).thenReturn(rooms)

        val expected = UiState(data = rooms)

        viewModel.loadRooms()

        assertEquals(expected, state.value)
    }

    @Test
    fun `load rooms fails updates state exception`() = runBlockingTest {
        val viewModel = viewModelFactory(this)

        val exception = IOException()
        whenever(roomsRepository.getRooms()).then { throw exception }

        viewModel.loadRooms()

        assert(state.value.exception is RuntimeException)
        assert(state.value.exception?.cause is IOException)
    }

    @Test
    fun `book room success updates data`() = runBlockingTest {
        val viewModel = viewModelFactory(this)
        val room = Room(
            name = "${Random.nextInt()}",
            spots = Random.nextInt(),
            thumbnail = "${Random.nextInt()}"
        )

        val list = listOf(room.copy(isBooked = true))

        whenever(roomsRepository.bookRoom(room)).thenReturn(list)

        val expected = UiState(data = list)

        viewModel.bookRoom(room)

        assertEquals(expected, state.value)
    }

    @Test
    fun `book room fails updates state exception`() = runBlockingTest {
        val viewModel = viewModelFactory(this)
        val room = Room(
            name = "${Random.nextInt()}",
            spots = Random.nextInt(),
            thumbnail = "${Random.nextInt()}"
        )

        val exception = IOException()
        whenever(roomsRepository.bookRoom(room)).then { throw exception }

        viewModel.bookRoom(room)

        assert(state.value.exception is RuntimeException)
        assert(state.value.exception?.cause is IOException)
    }
}
