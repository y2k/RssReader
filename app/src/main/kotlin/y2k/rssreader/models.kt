package y2k.rssreader

/**
 * Created by y2k on 17/08/16.
 */

data class RssItem(val title: String, val description: String)

typealias RssItems = List<RssItem>

data class RssSubscription(val title: String, val url: String)

typealias Subscriptions = List<RssSubscription>