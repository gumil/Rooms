package dev.gumil.rooms.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomsList(
    @Json(name = "rooms")
    val rooms: List<Room>
)

@Entity
@JsonClass(generateAdapter = true)
data class Room(
    @PrimaryKey
    @Json(name = "name")
    val name: String,
    @Json(name = "spots")
    val spots: Int,
    @Json(name = "thumbnail")
    val thumbnail: String,

    val isBooked: Boolean = false
)

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "success")
    val success: Boolean
)
