package y2k.rssreader

import io.reactivex.Single
import org.junit.Assert
import org.junit.Test

/**
 * Created by y2k on 17/08/16.
 */

class Tests {

    @Test fun test() {
        val rssItems = getRssItems({
            Single.just("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss><item>sdfdf</item></rss>")
        }).toBlocking().single()

        Assert.assertEquals(10, rssItems.size)
    }
}