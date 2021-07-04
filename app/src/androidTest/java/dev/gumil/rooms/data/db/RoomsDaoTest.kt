package dev.gumil.rooms.data.db

import android.content.Context
import androidx.room.Room as RoomDb
import androidx.test.core.app.ApplicationProvider
import dev.gumil.rooms.data.Room
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class RoomsDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db: RoomsDatabase = RoomDb
        .inMemoryDatabaseBuilder(context, RoomsDatabase::class.java)
        .build()
    private val roomsDao = db.roomsDao()

    @Test
    fun insertRoomAndGetAllRooms() {
        val room = room()
        val expected = listOf(room)

        roomsDao.insert(listOf(room))
        val actual = roomsDao.getAllRooms()

        assertEquals(expected, actual)
    }

    @Test
    fun insertRoomListAndReplaceDuplicateRooms() {
        val room = room()
        val expected = listOf(room)

        roomsDao.insert(listOf(room))
        roomsDao.insert(listOf(room))
        roomsDao.insert(listOf(room))
        val actual = roomsDao.getAllRooms()

        assertEquals(expected, actual)
    }

    @Test
    fun updateRoom() {
        val room = room()
        val bookedRoom = room.copy(isBooked = true)
        val expected = listOf(bookedRoom)

        roomsDao.insert(listOf(room))
        roomsDao.update(bookedRoom)
        val actual = roomsDao.getAllRooms()

        assertEquals(expected, actual)
    }

    @After
    fun closeDb() {
        db.close()
    }

    private fun room() =  Room(
        name = "${Random.nextInt()}",
        spots = Random.nextInt(),
        thumbnail = "${Random.nextInt()}"
    )
}
