package y2k.rssreader

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertArrayEquals
import org.junit.Test

/**
 * Created by y2k on 17/08/16.
 */

class Tests {

    @Test fun syncTest() {
        var actual = emptyList<RssItem>()
        syncRssWithWeb(
            loadRss = { Single.just(loadResource("example.xml")) },
            saveToRepo = { actual = it })

        assertArrayEquals(
            arrayOf(
                RssItem(
                    "Meet JetBrains at GDC Europe 2016",
                    "Cologne, Germany welcomes the world famous Game Development Conference again this August. In just 10 days hundreds of professionals from the gaming industry will meet at the biggest European event in this area. JetBrains will join with ReSharper, Rider, CLion &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/08/05/meet-jetbrains-at-gdc-europe-2016/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "Introducing JetBrains Toolbox App",
                    "Last summer we held our third annual two-day hackathon, an event where anyone and everyone from JetBrains (as well as a few external contributors) gathered to work on a novel, high-impact idea. The JetBrains App Launcher was one of these &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/05/25/introducing-jetbrains-toolbox-app/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "Issue Tracking Tools Survey",
                    "Dear Developers and Development Teams, We want to know what you think! We are conducting a study to learn more about the Issue Tracking tools that professionals use in their work process, and would very much appreciate your input. As &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/05/17/issue-tracking-tools-survey/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "Security update for IntelliJ-based IDEs v2016.1 and older versions",
                    "We have just released an important update for all IntelliJ-based IDEs. This update addresses critical security vulnerabilities inside the underlying IntelliJ Platform. The vulnerabilities, in various forms, are also present in older versions of the IDEs; therefore, patches for those &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/05/11/security-update-for-intellij-based-ides-v2016-1-and-older-versions/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "JetBrains Toolbox 2016.1 release is complete",
                    "We usually say a release is ‘available’ but ‘complete’ seems more appropriate for 2016.1. It took a whole month and many teams working together to publish all of the 2016.1 updates inside of JetBrains Toolbox. Here’s a short recap of &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/04/28/jetbrains-toolbox-2016-1-release-is-complete/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "JetBrains Toolbox—Release and Versioning Changes",
                    "With the shift to subscriptions, one of our goals was to move away from the one major release per year model, focusing on continuously delivering value independently of versioning. On changing to this model, a question that came up was, &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/03/09/jetbrains-toolbox-release-and-versioning-changes/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "Mobile app development survey",
                    "As mobile development is growing and a lot of JetBrains tools get increasingly exposed to mobile developers, we are looking to adapt and ensure the best experience. However, most of us are not particularly experienced in mobile development tech, which &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/02/22/mobile-app-development-survey/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "JetBrains Student Program: How to Renew & Graduation Discount",
                    "It’s been a bit more than a year since we first launched our free Student License Program, and today we are really pleased that more than 230,000 students worldwide have made the choice to use JetBrains tools for educational purposes. &#8230; <a href=\"https://blog.jetbrains.com/blog/2015/12/11/jetbrains-student-program-how-to-renew-graduation-discount/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "The Drive To Develop",
                    "In 2000, three friends set out to make their work easier by creating a tool that would remove mundane and somewhat inefficient tasks. This tool was called Renamer, and it had one job which was to rename things, a process &#8230; <a href=\"https://blog.jetbrains.com/blog/2015/12/10/the-drive-to-develop/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
                RssItem(
                    "Meet Us at Apps World in London, November 18-19th",
                    "The Apps World event returns to Excel, London. A leading multi-platform event in the app industry, Apps World features 350 exhibitors and is expected to attract 10,000+ attendees. With 52% of Apps World attendees being developers, this is the place &#8230; <a href=\"https://blog.jetbrains.com/blog/2015/11/06/meet-us-at-apps-world-in-london/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>")),
            actual.toTypedArray())
    }

    @Test fun test() {
        val expected = listOf(
            RssItem(
                "Meet JetBrains at GDC Europe 2016",
                "Cologne, Germany welcomes the world famous Game Development Conference again this August. In just 10 days hundreds of professionals from the gaming industry will meet at the biggest European event in this area. JetBrains will join with ReSharper, Rider, CLion &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/08/05/meet-jetbrains-at-gdc-europe-2016/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>"),
            RssItem(
                "Introducing JetBrains Toolbox App",
                "Last summer we held our third annual two-day hackathon, an event where anyone and everyone from JetBrains (as well as a few external contributors) gathered to work on a novel, high-impact idea. The JetBrains App Launcher was one of these &#8230; <a href=\"https://blog.jetbrains.com/blog/2016/05/25/introducing-jetbrains-toolbox-app/\">Continue reading <span class=\"meta-nav\">&#8594;</span></a>")
        )
        val actual = getRssItems({ Completable.complete() }, { expected }).getLast()

        assertArrayEquals(
            expected.toTypedArray(),
            actual.toTypedArray())
    }
}