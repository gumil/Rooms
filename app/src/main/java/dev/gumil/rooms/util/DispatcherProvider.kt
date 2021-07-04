package dev.gumil.rooms.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

class AndroidDispatcherProvider : DispatcherProvider {
    override val io get() = Dispatchers.IO
    override val main get() = Dispatchers.Main
}
