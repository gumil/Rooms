package dev.gumil.rooms.data.repository

import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.db.RoomsDao
import dev.gumil.rooms.data.network.RoomsApi
import okio.IOException
import timber.log.Timber

class NetworkLocalRoomsRepository(
    private val roomsApi: RoomsApi,
    private val roomsDao: RoomsDao
) : RoomsRepository {

    override suspend fun getRooms(): List<Room> {
        return try {
            val localRooms = roomsDao.getAllRooms()

            if (localRooms.isNotEmpty()) return localRooms

            val rooms = roomsApi.getRooms().rooms
            roomsDao.insert(rooms)
            rooms
        } catch (e: IOException) {
            Timber.w(e)
            emptyList()
        }
    }

    override suspend fun bookRoom(room: Room): List<Room> {
        roomsDao.update(room.copy(spots = room.spots - 1, isBooked = true))
        return roomsDao.getAllRooms()
    }
}
