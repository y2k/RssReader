package y2k.rssreader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import y2k.rssreader.components.RssComponent

class MainActivity : AppCompatActivity() {

    val list by lazy { findViewById(R.id.list) as RssComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setDataSource(dataSource)
        syncRss()
    }

    private val dataSource = getRssItems(::loadFromRepo)
    private val syncRss = { syncRssWithWeb(::loadResourceFromWeb, ::saveToRepo) }
}