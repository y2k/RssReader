package y2k.rssreader

import android.os.Handler
import android.os.Looper
import rx.Completable
import rx.Observable
import rx.Single
import rx.schedulers.Schedulers

/**
 * Created by y2k on 18/08/16.
 */

object Provider {

    fun provideGetDataSource(): () -> Observable<List<RssItem>> = { getRssItems(provideSync(), ::loadFromRepo) }
    fun provideSync(): () -> Completable = { syncRssWithWeb(provideLoadFromWeb(), ::saveToRepo) }
    fun provideLoadFromWeb(): (String) -> Single<String> = { loadFromWebCached(it, ::loadFromWeb, ::loadDateFromRepo, ::saveDateToRepo) }
}

fun <T> doAsync(action: () -> T): Single<T> {
    return Single
        .fromCallable(action)
        .subscribeOn(Schedulers.io())
        .observeOn(FOREGROUND_SCHEDULER)
}

private val FOREGROUND_SCHEDULER by lazy {
    Handler(Looper.getMainLooper())
        .let { h -> Schedulers.from { h.post(it) } }
}