package y2k.rssreader

import android.os.AsyncTask.THREAD_POOL_EXECUTOR
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import rx.Completable
import rx.Observable
import rx.Single
import rx.Subscription
import rx.subjects.AsyncSubject
import rx.subjects.BehaviorSubject
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

fun <T> runAsync(action: () -> T): Single<T> {
    val subject = AsyncSubject.create<T>()
    THREAD_POOL_EXECUTOR.execute {
        try {
            val result = action()
            FOREGROUND_HANDLER.post {
                subject.onNext(result)
                subject.onCompleted()
            }
        } catch (e: Exception) {
            FOREGROUND_HANDLER.post {
                subject.onError(e)
            }
        }
    }
    return subject.toSingle()
}

private val FOREGROUND_HANDLER by lazy { Handler(Looper.getMainLooper()) }

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

fun <T> Observable<T>.toLiveCycleObservable(activity: BaseActivity): Observable<T> {
    val subject = BehaviorSubject.create<T>()
    var subscription: Subscription? = null

    activity.onResume.subscribe {
        subscription = subscribe(
            { subject.onNext(it) },
            { subject.onError(it) },
            { subject.onCompleted() })
    }
    activity.onPause.subscribe {
        subscription?.unsubscribe()
    }

    return subject
}