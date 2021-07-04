package dev.gumil.rooms.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomsList(
    @Json(name = "rooms")
    val rooms: List<Room>
)

@JsonClass(generateAdapter = true)
data class Room(
    @Json(name = "name")
    val name: String,
    @Json(name = "spots")
    val spots: Int,
    @Json(name = "thumbnail")
    val thumbnail: String
)

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "success")
    val success: Boolean
)
