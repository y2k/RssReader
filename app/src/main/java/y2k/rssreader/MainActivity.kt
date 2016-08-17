package y2k.rssreader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import y2k.rssreader.components.RssComponent

class MainActivity : AppCompatActivity() {

    val list by lazy { findViewById(R.id.list) as RssComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getDataSource = provideGetDataSource()
        list.setDataSource(getDataSource())
    }
}

fun provideGetDataSource(): () -> Observable<List<RssItem>> = { getRssItems(provideSync(), ::loadFromRepo) }
fun provideSync(): () -> Unit = { syncRssWithWeb(provideLoadFromWeb(), ::saveToRepo) }
fun provideLoadFromWeb(): (String) -> Single<String> = { loadFromWebCached(it, ::loadFromWeb, ::loadDateFromRepo, ::saveDateToRepo) }