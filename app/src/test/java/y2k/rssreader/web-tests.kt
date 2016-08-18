package y2k.rssreader

import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by y2k on 17/08/16.
 */

class WebTests {

    @Test
    fun test() {
        fun load(date: Date): Single<String> {
            return loadFromWebCached("", { Single.just("data") }, { date }, { url, data -> })
        }

        assertEquals("data", load(Date(0L)).blockingGet())
        assertEquals("data", load(Date().offset(-70 * 1000)).blockingGet())
        assertEquals("error", load(Date().offset(-40 * 1000)).onErrorReturn { "error" }.blockingGet())
    }

    private fun Date.offset(offset: Long) = Date(this.time + offset)
}