package y2k.rssreader.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.ListView
import rx.Observable
import y2k.rssreader.Subscriptions

/**
 * Created by y2k on 21/08/16.
 */
class SubscriptionComponent(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {

    fun init(subscriptions: Observable<Subscriptions>) {
        subscriptions.subscribe {
            adapter = it
                .map { it.title }
                .let { ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, it) }
        }
    }
}