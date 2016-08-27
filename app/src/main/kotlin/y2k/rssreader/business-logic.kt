package y2k.rssreader

import java8.util.concurrent.CompletableFuture
import org.jsoup.Jsoup
import rx.Observable
import rx.subjects.ReplaySubject

/**
 * Created by y2k on 17/08/16.
 */

fun getSubscriptions(): Observable<Subscriptions> = Observable.just(listOf(
    RssSubscription("JetBrains blog", "https://blog.jetbrains.com/feed/"),
    RssSubscription("Kotlin blog", "http://feeds.feedburner.com/kotlin")))

fun getRssItems(syncRssWithWeb: () -> CompletableFuture<*>, loadFromRepo: () -> RssItems): Observable<RssItems> {
    val subject = ReplaySubject.createWithSize<RssItems>(1)
    subject.onNext(loadFromRepo())
    syncRssWithWeb()
        .whenComplete { result, throwable ->
            if (throwable == null) {
                subject.onNext(loadFromRepo())
                subject.onCompleted()
            } else {
                throwable.printStackTrace()
                subject.onCompleted()
            }
        }
    return subject
}

fun syncRssWithWeb(loadRss: (String) -> CompletableFuture<String>, saveToRepo: (RssItems) -> Unit): CompletableFuture<*> {
    return loadRss("https://blog.jetbrains.com/feed/")
        .thenApply(::parse)
        .doOnSuccess { saveToRepo(it) }
}

private fun parse(rss: String): List<RssItem> {
    return Jsoup.parse(rss)
        .select("item")
        .map {
            RssItem(
                it.select("title").text(),
                it.select("description").text().unescapeHtml())
        }
}

private val htmlRegex = Regex("&#(\\d+);")
private fun String.unescapeHtml(): String {
    return replace(htmlRegex) {
        it.groupValues[1].toInt().toChar().toString()
    }
}
