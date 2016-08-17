package y2k.rssreader

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by y2k on 17/08/16.
 */

fun saveToRepo(items: RssItems) {
    repo.clear()
    repo.addAllAbsent(items)
}

fun loadFromRepo(): RssItems = repo.toList()

private val repo = CopyOnWriteArrayList<RssItem>()