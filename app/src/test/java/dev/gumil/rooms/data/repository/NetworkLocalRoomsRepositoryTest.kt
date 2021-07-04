package dev.gumil.rooms.data.repository

import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.RoomsList
import dev.gumil.rooms.data.db.RoomsDao
import dev.gumil.rooms.data.network.RoomsApi
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random

class NetworkLocalRoomsRepositoryTest {
    private val api = mock<RoomsApi>()
    private val dao = mock<RoomsDao>()
    private val repository = NetworkLocalRoomsRepository(api, dao)

    @Test
    fun `getRooms return rooms from api`() = runBlocking {
        val room = room()
        val expected = listOf(room)
        whenever(api.getRooms()).thenReturn(RoomsList(expected))
        whenever(dao.getAllRooms()).thenReturn(emptyList())

        val actual = repository.getRooms()

        verify(dao).insert(expected)
        assertEquals(expected, actual)
    }

    @Test
    fun `getRooms return rooms from local database`() = runBlocking {
        val room = room()
        val expected = listOf(room)
        whenever(dao.getAllRooms()).thenReturn(expected)

        val actual = repository.getRooms()

        verify(dao, never()).insert(rooms = any())
        verify(api, never()).getRooms()
        assertEquals(expected, actual)
    }

    @Test
    fun `getRooms when api fails returns empty list`() = runBlocking {
        val expected = emptyList<Room>()
        whenever(api.getRooms()).then { throw IOException() }
        whenever(dao.getAllRooms()).thenReturn(emptyList())

        val actual = repository.getRooms()

        verify(dao, never()).insert(rooms = any())
        assertEquals(expected, actual)
    }

    @Test
    fun `bookRoom when api fails returns empty list`() = runBlocking {
        val roomToUpdate = room(isBooked = false)
        val rooms = listOf(
            room(),
            room(),
            room()
        )

        whenever(dao.getAllRooms()).thenReturn(rooms)

        val actual = repository.bookRoom(roomToUpdate)

        verify(dao).update(roomToUpdate.copy(
            spots = roomToUpdate.spots - 1,
            isBooked = true
        ))
        assertEquals(rooms, actual)
    }

    private fun room(isBooked: Boolean = false) =  Room(
        name = "${Random.nextInt()}",
        spots = Random.nextInt(),
        thumbnail = "${Random.nextInt()}",
        isBooked = isBooked
    )
}
