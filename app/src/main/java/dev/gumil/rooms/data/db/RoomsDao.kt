package dev.gumil.rooms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.gumil.rooms.data.Room

@Dao
interface RoomsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rooms: List<Room>)

    @Update
    fun update(room: Room)

    @Query("SELECT * FROM room")
    fun getAllRooms(): List<Room>
}
