package dev.gumil.rooms.di

import android.content.Context
import androidx.room.Room
import dev.gumil.rooms.data.db.RoomsDatabase
import dev.gumil.rooms.data.network.ApiFactory
import dev.gumil.rooms.data.repository.NetworkLocalRoomsRepository
import dev.gumil.rooms.data.repository.RoomsRepository
import dev.gumil.rooms.util.AndroidDispatcherProvider
import dev.gumil.rooms.util.DispatcherProvider

interface AppContainer {
    val roomsRepository: RoomsRepository
    val dispatcherProvider: DispatcherProvider
}

class RoomsAppContainer(
    private val context: Context
) : AppContainer {

    private val db by lazy {
        Room.databaseBuilder(
            context,
            RoomsDatabase::class.java,
            "rooms_db"
        ).build()
    }

    private val api by lazy {
        ApiFactory.createApi()
    }

    override val roomsRepository: RoomsRepository by lazy {
        NetworkLocalRoomsRepository(api, db.roomsDao())
    }
    override val dispatcherProvider: DispatcherProvider by lazy {
        AndroidDispatcherProvider()
    }
}
