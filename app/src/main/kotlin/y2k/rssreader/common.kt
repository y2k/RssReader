package y2k.rssreader

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import java8.util.concurrent.CompletableFuture
import org.funktionale.partials.partially1
import rx.Observable
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

/**
 * Created by y2k on 18/08/16.
 */

object Provider {

    fun getRssItems(): Observable<RssItems> = getRssItems(provideSync(), ::loadFromRepo)

    private fun provideSync(): () -> CompletableFuture<*> =
        ::syncRssWithWeb
            .partially1(provideLoadFromWeb())
            .partially1(::saveToRepo)

    private fun provideLoadFromWeb(): (String) -> CompletableFuture<String> =
        ::loadFromWebCached
            .partially1(::loadFromWeb)
            .partially1(::loadDateFromRepo)
            .partially1(::saveDateToRepo)

    fun selectSubscription(subscription: RssSubscription) {
        TODO()
    }
}

inline fun <T> CompletableFuture<T>.peek(crossinline f: (T) -> Unit): CompletableFuture<T> = thenApply { f(it); it }

fun <P1, R> Function1<P1, R>.partially1(p1: P1): () -> R = { this(p1) }

fun <T> runAsync(action: () -> T): CompletableFuture<T> {
    val promise = CompletableFuture<T>()
    CompletableFuture.supplyAsync {
        try {
            val result = action()
            FOREGROUND_HANDLER.post { promise.complete(result) }
        } catch (e: Exception) {
            FOREGROUND_HANDLER.post { promise.obtrudeException(e) }
        }
    }
    return promise
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

fun <T> Observable<T>.toLiveCycleObservable(context: Context): Observable<T> {
    val activity = context as BaseActivity
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