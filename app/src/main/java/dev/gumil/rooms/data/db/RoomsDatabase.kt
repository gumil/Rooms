package dev.gumil.rooms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.gumil.rooms.data.Room

@Database(entities = [Room::class], version = 1)
abstract class RoomsDatabase : RoomDatabase() {
    abstract fun roomsDao(): RoomsDao
}
