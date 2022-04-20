//@file:Suppress("UNUSED")
//
//package com.github.arhor.linden.dragon.tavern.common.coroutine
//
//import kotlinx.coroutines.*
//import kotlinx.coroutines.CoroutineStart.LAZY
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import java.nio.ByteBuffer
//import java.nio.channels.AsynchronousFileChannel
//import java.nio.channels.CompletionHandler
//import java.nio.file.Paths
//import kotlin.coroutines.CoroutineContext
//import kotlin.coroutines.resume
//import kotlin.coroutines.resumeWithException
//import kotlin.coroutines.suspendCoroutine
//
//internal fun <T> lazyAsync(
//    context: CoroutineContext = Dispatchers.Unconfined,
//    scope: CoroutineScope = CoroutineScope(context),
//    block: suspend CoroutineScope.() -> T
//): Deferred<T> {
//    return scope.async(context, start = LAZY, block)
//}
//
//fun chunked(fileName: String, chunkSize: Int = DEFAULT_BUFFER_SIZE): Flow<Pair<ByteBuffer, Int>> {
//
//    val buffer = ByteBuffer.allocate(chunkSize)
//
//    AsynchronousFileChannel.open(Paths.get(fileName)).use { channel ->
//
//        return flow {
//            var numRead: Int
//            while (channel.readAsync(buffer).also { numRead = it } != -1) {
//                if (numRead > 0) {
//                    emit(buffer to numRead)
//                }
//            }
//        }
//    }
//}
//
//// TODO:
//suspend fun AsynchronousFileChannel.readAsync(buffer: ByteBuffer): Int {
//
//    return suspendCoroutine { continuation ->
//        read(buffer, 0L, Unit, object : CompletionHandler<Int, Unit> {
//
//            override fun completed(bytesRead: Int, attachment: Unit) {
//                continuation.resume(bytesRead)
//            }
//
//            override fun failed(exception: Throwable, attachment: Unit) {
//                continuation.resumeWithException(exception)
//            }
//        })
//    }
//}
