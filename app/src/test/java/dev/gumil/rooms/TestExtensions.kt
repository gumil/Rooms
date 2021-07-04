package dev.gumil.rooms

import okhttp3.mockwebserver.MockResponse
import java.io.ByteArrayOutputStream

private const val BUFFER_SIZE = 1024
private const val SUCCESS_RESPONSE_CODE = 200

fun Any.readFromFile(file: String): String {
    val inputStream = this.javaClass.classLoader!!.getResourceAsStream(file)
    val result = ByteArrayOutputStream()
    val buffer = ByteArray(BUFFER_SIZE)
    var length = inputStream.read(buffer)
    while (length != -1) {
        result.write(buffer, 0, length)
        length = inputStream.read(buffer)
    }
    return result.toString("UTF-8")
}

fun Any.createMockResponse(file: String): MockResponse {
    val body = readFromFile(file)
    return MockResponse().apply {
        setResponseCode(SUCCESS_RESPONSE_CODE)
        setBody(body)
        addHeader("Content-type: application/json")
    }
}
