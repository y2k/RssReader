package y2k.rssreader

import android.os.Handler
import android.os.Looper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.URL

/**
 * Created by y2k on 17/08/16.
 */

fun loadResourceFromWeb(url: String): Single<String> = doAsync { URL(url).readText() }

private fun <T> doAsync(action: () -> T): Single<T> {
    return Single
        .fromCallable(action)
        .subscribeOn(Schedulers.io())
        .observeOn(FOREGROUND_SCHEDULER)
}

private val FOREGROUND_SCHEDULER = Handler(Looper.getMainLooper())
    .let { h -> Schedulers.from { h.post(it) } }