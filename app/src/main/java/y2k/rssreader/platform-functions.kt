package y2k.rssreader

import android.os.Handler
import android.os.Looper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.URL

/**
 * Created by y2k on 17/08/16.
 */

fun loadResourceFromWeb(url: String): Single<String> {
    return doAsync { URL(url).readText() }
}

private val FOREGROUND_SCHEDULER = Handler(Looper.getMainLooper())
    .let { h -> Schedulers.from { h.post(it) } }

private fun <T> doAsync(action: () -> T): Single<T> {
    return Single
        .create<T> { subscriber ->
            try {
                subscriber.onSuccess(action())
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
        .subscribeOn(Schedulers.io())
        .observeOn(FOREGROUND_SCHEDULER)
}
