package y2k.rssreader

import android.os.Bundle
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