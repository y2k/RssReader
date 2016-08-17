package y2k.rssreader

import io.reactivex.Observable
import io.reactivex.Single
import org.jsoup.Jsoup

/**
 * Created by y2k on 17/08/16.
 */

fun getRssItems(loadRss: (String) -> Single<String>): Observable<RssItems> {
    return loadRss("https://blog.jetbrains.com/feed/")
        .map(::parse)
        .toObservable()
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