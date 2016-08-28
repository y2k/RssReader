package y2k.rssreader

import org.junit.Assert.assertEquals
import org.junit.Test
import rx.AsyncEmitter
import rx.Observable
import rx.Single
import rx.subjects.AsyncSubject

/**
 * Created by y2k on 21/08/16.
 */
class RxTests {

    @Test fun `fromAsync test`() {
        var mode = 0
        val o = Observable.fromAsync<RssItems>({ emitter ->
            mode = 1
            emitter.setCancellation {
                mode = 2
            }
        }, AsyncEmitter.BackpressureMode.BUFFER)
        assertEquals(0, mode)

        val s = o.subscribe {
        }
        assertEquals(1, mode)

        s.unsubscribe()
        assertEquals(2, mode)
    }

    @Test fun `AsyncSubject test`() {
        var index = 1
        fun f(): Single<Int> {
            val s = AsyncSubject.create<Int>()
            index++
            s.onNext(index)
            s.onCompleted()
            return s.toSingle()
        }
        assertEquals(1, index)

        val single = f()
        assertEquals(2, index)

        single.subscribe { assertEquals(2, it) }
        assertEquals(2, index)

        single.subscribe { assertEquals(2, it) }
        assertEquals(2, index)
    }
}