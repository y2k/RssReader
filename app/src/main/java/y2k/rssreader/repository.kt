package y2k.rssreader

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by y2k on 17/08/16.
 */

fun saveDateToRepo(url: String, cached: Date) {
    dateRepo.put(url, cached)
}

fun loadDateFromRepo(url: String): Date = dateRepo[url] ?: Date(0L)

fun saveToRepo(items: RssItems) {
    rssItemsRepo.clear()
    rssItemsRepo.addAll(items)
}

fun loadFromRepo(): RssItems = rssItemsRepo.toList()

private val rssItemsRepo = Vector<RssItem>()
private val dateRepo = ConcurrentHashMap<String, Date>()