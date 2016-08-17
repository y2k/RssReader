package y2k.rssreader

import io.reactivex.Observable
import io.reactivex.Single
import org.jsoup.Jsoup

/**
 * Created by y2k on 17/08/16.
 */

fun getRssItems(syncRssWithWeb: () -> Unit, loadFromRepo: () -> RssItems): Observable<RssItems> {
    syncRssWithWeb()
    return Observable.just(loadFromRepo())
}

fun syncRssWithWeb(loadRss: (String) -> Single<String>, saveToRepo: (RssItems) -> Unit) {
    loadRss("https://blog.jetbrains.com/feed/")
        .map(::parse)
        .subscribe({ it: RssItems -> saveToRepo(it) }, { it.printStackTrace() })
}

private fun parse(rss: String): List<RssItem> {
    return Jsoup.parse(rss)
        .select("item")
        .map {
            RssItem(
                it.select("title").text(),
                it.select("description").text())
        }
}