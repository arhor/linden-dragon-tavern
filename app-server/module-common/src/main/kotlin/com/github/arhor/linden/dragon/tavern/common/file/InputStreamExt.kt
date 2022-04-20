package com.github.arhor.linden.dragon.tavern.common.file

import java.io.InputStream

typealias ChunkConsumer = (chunk: ByteArray, size: Int) -> Unit

/**
 * Reads this input stream by chunks of provided size. Closes provided input stream.
 */
inline fun InputStream.chunked(chunkSize: Int = DEFAULT_BUFFER_SIZE, consumer: ChunkConsumer) {
    val buffer = ByteArray(chunkSize)
    val stream = buffered()

    var numRead: Int

    while (stream.read(buffer).also { numRead = it } != -1) {
        if (numRead > 0) {
            consumer(buffer, numRead)
        }
    }
}
