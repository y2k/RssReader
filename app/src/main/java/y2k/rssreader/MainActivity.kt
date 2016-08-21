package y2k.rssreader

import android.os.Bundle
import y2k.rssreader.Provider.getRssItems
import y2k.rssreader.components.RssComponent
import y2k.rssreader.components.SubscriptionComponent

class MainActivity : BaseActivity() {

    val list by lazy { findViewById(R.id.list) as RssComponent }
    val subscriptions by lazy { findViewById(R.id.subscriptions) as SubscriptionComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRssItems()
            .toLiveCycleObservable(this)
            .let { list.init(it) }
        getSubscriptions()
            .toLiveCycleObservable(this)
            .let { subscriptions.init(it) }
    }
}