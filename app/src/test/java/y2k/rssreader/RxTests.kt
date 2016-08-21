package y2k.rssreader

import org.junit.Assert.assertEquals
import org.junit.Test
import rx.Single
import rx.subjects.AsyncSubject

/**
 * Created by y2k on 21/08/16.
 */
class RxTests {

    @Test fun test() {
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