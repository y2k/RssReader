package y2k.rssreader.components

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import rx.Observable
import y2k.rssreader.RssItems

/**
 * Created by y2k on 17/08/16.
 */
@Suppress("DEPRECATION")
class RssComponent(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {

    fun init(items: Observable<RssItems>) {
        items.subscribe { items ->
            adapter = object : BaseAdapter() {

                override fun getView(index: Int, view: View?, p2: ViewGroup?): View {
                    val v = view ?: View.inflate(context, android.R.layout.simple_list_item_2, null)
                    val s = items[index]
                    (v.findViewById(android.R.id.text1) as TextView).text = s.title
                    (v.findViewById(android.R.id.text2) as TextView).text = Html.fromHtml(s.description)
                    return v
                }

                override fun getItemId(index: Int): Long = index.toLong()
                override fun getCount(): Int = items.size
                override fun getItem(index: Int): Any = TODO()
            }
        }
    }
}