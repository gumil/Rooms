package dev.gumil.rooms.data.repository

import dev.gumil.rooms.data.Room
import dev.gumil.rooms.data.db.RoomsDao
import dev.gumil.rooms.data.network.RoomsApi
import okio.IOException
import timber.log.Timber

class NetworkLocalRoomsRepository(
    private val roomsApi: RoomsApi,
    private val roomsDao: RoomsDao
): RoomsRepository {

    override suspend fun getRooms(): List<Room> {
        return try {
            val rooms = roomsApi.getRooms().rooms
            roomsDao.insert(rooms)
            rooms
        } catch (e: IOException) {
            Timber.w(e)
            roomsDao.getAllRooms()
        }
    }
}
