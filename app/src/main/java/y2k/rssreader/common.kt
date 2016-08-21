package y2k.rssreader

import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import rx.Completable
import rx.Observable
import rx.Single
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

/**
 * Created by y2k on 18/08/16.
 */

object Provider {

    fun provideGetDataSource(): () -> Observable<RssItems> = ::getRssItems.curried(provideSync(), ::loadFromRepo)
    fun provideSync(): () -> Completable = ::syncRssWithWeb.curried(provideLoadFromWeb(), ::saveToRepo)
    fun provideLoadFromWeb(): (String) -> Single<String> = ::loadFromWebCached.curried(::loadFromWeb, ::loadDateFromRepo, ::saveDateToRepo)
}

fun <T1, T2, R> Function2<T1, T2, R>.curried(t1: T1, t2: T2): () -> R = { invoke(t1, t2) }
fun <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R>.curried(t1: T1, t2: T2, t3: T3): (T4) -> R = { invoke(t1, t2, t3, it) }

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

open class BaseActivity : AppCompatActivity() {

    val onResume: PublishSubject<Any> = PublishSubject.create()
    val onPause: PublishSubject<Any> = PublishSubject.create()

    override fun onResume() {
        super.onResume()
        onResume.onNext(Unit)
    }

    override fun onPause() {
        super.onPause()
        onPause.onNext(Unit)
    }
}