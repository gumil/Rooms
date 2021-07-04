package dev.gumil.rooms.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider: DispatcherProvider {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher = testCoroutineDispatcher
    override val main: CoroutineDispatcher = testCoroutineDispatcher
}
