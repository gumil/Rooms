package dev.gumil.rooms.data.network

import dev.gumil.rooms.data.ApiResponse
import dev.gumil.rooms.data.RoomsList
import retrofit2.http.GET

interface RoomsApi {

    @GET("/rooms.json")
    suspend fun getRooms(): RoomsList

    @GET("/bookRoom.json")
    suspend fun bookRoom(): ApiResponse
}
