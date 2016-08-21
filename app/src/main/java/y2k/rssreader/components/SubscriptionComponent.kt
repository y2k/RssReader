package y2k.rssreader.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import y2k.rssreader.Provider.selectSubscription
import y2k.rssreader.RssSubscription
import y2k.rssreader.getSubscriptions
import y2k.rssreader.toLiveCycleObservable

/**
 * Created by y2k on 21/08/16.
 */
class SubscriptionComponent(context: Context, attrs: AttributeSet?) : ListView(context, attrs) {

    init {
        getSubscriptions()
            .toLiveCycleObservable(context)
            .subscribe { subs ->
                adapter = object : BaseAdapter() {

                    override fun getView(index: Int, view: View?, parent: ViewGroup?): View {
                        val v = view ?: View.inflate(context, android.R.layout.simple_list_item_2, null)
                        val i = subs[index]
                        (v.findViewById(android.R.id.text1) as TextView).text = i.title
                        (v.findViewById(android.R.id.text2) as TextView).text = i.url
                        return v
                    }

                    override fun getCount() = subs.size
                    override fun getItemId(index: Int) = index.toLong()
                    override fun getItem(index: Int) = TODO()
                }
            }

        setOnItemClickListener { adapterView, view, index, id ->
            selectSubscription(adapter.getItem(index) as RssSubscription)
        }
    }
}