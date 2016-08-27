package y2k.rssreader

import java8.util.concurrent.CompletableFuture
import java.net.URL
import java.util.*

/**
 * Created by y2k on 17/08/16.
 */

fun loadFromWebCached(
    loadFromWeb: (String) -> CompletableFuture<String>,
    getCached: (String) -> Date,
    setCached: (String, Date) -> Unit,
    url: String): CompletableFuture<String> {

    val expire = Date().time - getCached(url).time
    return if (expire < 60 * 1000)
        CompletableFuture.failedFuture(IllegalStateException("Cache not expired")) // TODO:
    else loadFromWeb(url).peek { setCached(url, Date()) }
}

fun loadFromWeb(url: String): CompletableFuture<String> = runAsync { URL(url).readText() }