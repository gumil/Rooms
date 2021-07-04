package dev.gumil.rooms.data.repository

import dev.gumil.rooms.data.Room

interface RoomsRepository {
    suspend fun getRooms(): List<Room>
    suspend fun bookRoom(room: Room): List<Room>
}
