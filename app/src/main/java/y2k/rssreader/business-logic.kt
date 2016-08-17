package y2k.rssreader

import io.reactivex.Observable
import io.reactivex.Single
import org.jsoup.Jsoup

/**
 * Created by y2k on 17/08/16.
 */

// https://blog.jetbrains.com/kotlin/comments/feed/
fun getRssItems(loadRss: (String) -> Single<String>): Observable<RssItems> {
    return loadRss("https://blog.jetbrains.com/kotlin/comments/feed/")
        .map { Jsoup.parse(it).select("item").map { RssItem("") } }
        .toObservable()
}