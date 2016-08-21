package y2k.rssreader

import rx.Single
import java.net.URL
import java.util.*

/**
 * Created by y2k on 17/08/16.
 */

fun loadFromWebCached(
    loadFromWeb: (String) -> Single<String>,
    getCached: (String) -> Date,
    setCached: (String, Date) -> Unit,
    url: String): Single<String> {
    val expire = Date().time - getCached(url).time
    return if (expire < 60 * 1000) Single.error(IllegalStateException("Cache not expired")) // TODO:
    else loadFromWeb(url).doOnSuccess { setCached(url, Date()) }
}

fun loadFromWeb(url: String): Single<String> = runAsync { URL(url).readText() }