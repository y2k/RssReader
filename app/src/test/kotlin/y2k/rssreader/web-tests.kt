package y2k.rssreader

import java8.util.concurrent.CompletableFuture
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by y2k on 17/08/16.
 */

class WebTests {

    @Test
    fun test() {
        fun load(date: Date): CompletableFuture<String> {
            return loadFromWebCached({ CompletableFuture.completedFuture("data") }, { date }, { url, data -> }, "")
        }

        assertEquals("data", load(Date(0L)).get())
        assertEquals("data", load(Date().offset(-70 * 1000)).get())
        assertEquals("error", load(Date().offset(-40 * 1000)).exceptionally { "error" }.get())
    }

    private fun Date.offset(offset: Long) = Date(this.time + offset)
}