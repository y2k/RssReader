package y2k.rssreader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import y2k.rssreader.components.RssComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById(R.id.list) as RssComponent
        list.setDataSource(getDataSource())
    }

    private fun getDataSource() = getRssItems(::loadResourceFromWeb)
}