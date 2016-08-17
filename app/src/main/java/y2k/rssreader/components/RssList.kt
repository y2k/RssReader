package y2k.rssreader.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import io.reactivex.Observable
import y2k.rssreader.RssItems

/**
 * Created by y2k on 17/08/16.
 */
class RssList(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {

    fun setDataSource(items: Observable<RssItems>) {
        TODO()
    }
}