package dev.gumil.rooms.data.network

import dev.gumil.rooms.createMockResponse
import dev.gumil.rooms.data.ApiResponse
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.RoomsList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class RoomsApiTest {
    private val mockServer = MockWebServer().apply { start() }
    private val api = ApiFactory.createApi(baseUrl = mockServer.url("/").toString())

    @Test
    fun `get rooms should return a list of rooms`() = runBlocking {
        mockServer.enqueue(createMockResponse("rooms.json"))
        val expected = RoomsList(
            listOf(
                Room(
                    name = "Ljerka",
                    spots = 43,
                    thumbnail = "ljerka"
                ),
                Room(
                    name = "Mostafa",
                    spots = 4,
                    thumbnail = "mostafa"
                )
            )
        )
        val actual = api.getRooms()

        assertEquals(expected, actual)
    }

    @Test
    fun `book room returns success`() = runBlocking {
        mockServer.enqueue(createMockResponse("bookRoom.json"))
        val expected = ApiResponse(true)
        val actual = api.bookRoom()

        assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
