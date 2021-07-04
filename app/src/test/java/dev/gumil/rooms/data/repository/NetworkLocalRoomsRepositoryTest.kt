package dev.gumil.rooms.data.repository

import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.RoomsList
import dev.gumil.rooms.data.db.RoomsDao
import dev.gumil.rooms.data.network.RoomsApi
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Assert.*
import org.junit.Test
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

        val actual = repository.getRooms()

        verify(dao).insert(expected)
        verify(dao, never()).getAllRooms()
        assertEquals(expected, actual)
    }

    @Test
    fun `getRooms when api fails get rooms from database`() = runBlocking {
        val room = room()
        val expected = listOf(room)
        whenever(api.getRooms()).then { throw IOException() }
        whenever(dao.getAllRooms()).thenReturn(expected)

        val actual = repository.getRooms()

        assertEquals(expected, actual)
    }

    private fun room() =  Room(
        name = "${Random.nextInt()}",
        spots = Random.nextInt(),
        thumbnail = "${Random.nextInt()}"
    )
}
