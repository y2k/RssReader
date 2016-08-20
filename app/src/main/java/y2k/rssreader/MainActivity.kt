package y2k.rssreader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import rx.Observable
import rx.subjects.BehaviorSubject
import y2k.rssreader.components.RssComponent

class MainActivity : AppCompatActivity() {

    val list by lazy { findViewById(R.id.list) as RssComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getDataSource = Provider.provideGetDataSource()
        val dataSource = toLiveCycleObservable(getDataSource())
        list.initialize(dataSource)
    }

    fun <T> toLiveCycleObservable(observable: Observable<T>): Observable<T> {

        val subject = BehaviorSubject.create<T>()

        TODO()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}