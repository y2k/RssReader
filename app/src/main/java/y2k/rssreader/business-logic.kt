package y2k.rssreader

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.jsoup.Jsoup

/**
 * Created by y2k on 17/08/16.
 */

fun getRssItems(syncRssWithWeb: () -> Completable, loadFromRepo: () -> RssItems): Observable<RssItems> {
    val subject = ReplaySubject.create<RssItems>()
    subject.onNext(loadFromRepo())
    syncRssWithWeb()
        .subscribe({
            subject.onNext(loadFromRepo())
            subject.onComplete()
        }, {
            it.printStackTrace()
            subject.onComplete()
        })
    return subject
}

fun syncRssWithWeb(loadRss: (String) -> Single<String>, saveToRepo: (RssItems) -> Unit): Completable {
    val subject = PublishSubject.create<Any>()
    loadRss("https://blog.jetbrains.com/feed/")
        .map(::parse)
        .doOnSuccess { saveToRepo(it) }
        .subscribe(
            { subject.onComplete() },
            { subject.onError(it) })
    return subject.toCompletable()
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