package y2k.rssreader

import org.jsoup.Jsoup
import rx.Completable
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject

/**
 * Created by y2k on 17/08/16.
 */

fun getSubscriptions(): Observable<Subscriptions> =
    Observable.just(listOf("JetBrains blog", "Kotlin blog").map { RssSubscription(it) })

fun getRssItems(syncRssWithWeb: () -> Completable, loadFromRepo: () -> RssItems): Observable<RssItems> {
    val subject = ReplaySubject.createWithSize<RssItems>(1)
    subject.onNext(loadFromRepo())
    syncRssWithWeb()
        .subscribe({
            subject.onNext(loadFromRepo())
            subject.onCompleted()
        }, {
            it.printStackTrace()
            subject.onCompleted()
        })
    return subject
}

fun syncRssWithWeb(loadRss: (String) -> Single<String>, saveToRepo: (RssItems) -> Unit): Completable {
    val subject = PublishSubject.create<Any>()
    loadRss("https://blog.jetbrains.com/feed/")
        .map(::parse)
        .doOnSuccess { saveToRepo(it) }
        .subscribe(
            { subject.onCompleted() },
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