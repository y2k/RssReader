package y2k.rssreader

import io.reactivex.Observable

/**
 * Created by y2k on 17/08/16.
 */

fun loadResource(name:String):String {
    return ClassLoader.getSystemResource(name).readText()
}

fun <T> Observable<T>.get(): T = toBlocking().single()