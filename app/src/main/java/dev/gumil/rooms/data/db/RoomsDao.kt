package dev.gumil.rooms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.gumil.rooms.data.Room

@Dao
interface RoomsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rooms: List<Room>)

    @Query("SELECT * FROM room")
    fun getAllRooms(): List<Room>
}
