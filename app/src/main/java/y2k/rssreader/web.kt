package y2k.rssreader

import android.os.Handler
import android.os.Looper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.URL
import java.util.*

/**
 * Created by y2k on 17/08/16.
 */

fun loadFromWebCached(
    url: String,
    loadFromWeb: (String) -> Single<String>,
    getCached: (String) -> Date,
    setCached: (String, Date) -> Unit): Single<String> {
    val expire = Date().time - getCached(url).time
    return if (expire < 60 * 1000) Single.error(Exception())
    else loadFromWeb(url).doOnSuccess { setCached(url, Date()) }
}

fun loadFromWeb(url: String): Single<String> = doAsync { URL(url).readText() }

private fun <T> doAsync(action: () -> T): Single<T> {
    return Single
        .fromCallable(action)
        .subscribeOn(Schedulers.io())
        .observeOn(FOREGROUND_SCHEDULER)
}

private val FOREGROUND_SCHEDULER by lazy {
    Handler(Looper.getMainLooper())
        .let { h -> Schedulers.from { h.post(it) } }
}