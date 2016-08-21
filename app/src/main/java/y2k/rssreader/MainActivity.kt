package y2k.rssreader

import android.os.Bundle
import rx.Observable
import rx.Subscription
import rx.subjects.BehaviorSubject
import y2k.rssreader.components.RssComponent

class MainActivity : BaseActivity() {

    val list by lazy { findViewById(R.id.list) as RssComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getDataSource = Provider.provideGetDataSource()
        list.initialize(getDataSource().toLiveCycleObservable(this))
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