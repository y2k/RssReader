package y2k.rssreader

import rx.Observable
import rx.Single

/**
 * Created by y2k on 17/08/16.
 */

fun loadResource(name: String): String {
    return ClassLoader.getSystemResource(name).readText()
}

fun <T> Observable<T>.get(): T = toBlocking().single()

fun <T> Observable<T>.getLast(): T = toBlocking().last()

fun <T> Single<T>.get(): T = toBlocking().value()