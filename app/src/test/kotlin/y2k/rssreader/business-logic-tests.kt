package y2k.rssreader

import java8.util.concurrent.CompletableFuture
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import rx.Completable
import rx.Single

/**
 * Created by y2k on 17/08/16.
 */

class Tests {

    @Test fun `get subscriptions`() {
        assertArrayEquals(
            arrayOf(
                RssSubscription("JetBrains blog", "https://blog.jetbrains.com/feed/"),
                RssSubscription("Kotlin blog", "http://feeds.feedburner.com/kotlin")),
            getSubscriptions().get().toTypedArray())
    }

    @Test fun `sync items for kotlin blog`() {
        var actual = emptyList<RssItem>()
        syncRssWithWeb(
            loadRss = { CompletableFuture.completedFuture(loadResource("kotlin.xml")) },
            saveToRepo = { actual = it })

        assertArrayEquals(
            arrayOf(
                RssItem(
                    "Calling on EAPers",
                    "At JetBrains we’ve always believed in our Early Access Program for our tools, giving developers a chance to use the latest features or fixes as soon as they’re ready. We’ve followed the same philosophy with Kotlin, knowing of course that … <a href=\"https://blog.jetbrains.com/kotlin/2016/08/calling-on-eapers/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "First glimpse of Kotlin 1.1: Coroutines, Type aliases and more",
                    "While Kotlin 1.0.X releases keep delivering incremental updates and tooling features, we are working on the new language features in Kotlin 1.1. Today we are presenting the first preview version of 1.1, it’s far from Beta, but the brave and … <a href=\"https://blog.jetbrains.com/kotlin/2016/07/first-glimpse-of-kotlin-1-1-coroutines-type-aliases-and-more/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin 1.0.3 Is Here!",
                    "We are delighted to present Kotlin 1.0.3. This update is not full of brand new and shiny features, it is more about bug fixes, tooling improvements and performance boosts. That’s why you’ll like it 😉 Take a look at the … <a href=\"https://blog.jetbrains.com/kotlin/2016/06/kotlin-1-0-3-is-here/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin Night in San Francisco Recordings",
                    "On May 17th we held an evening event at San Francisco in cooperation with Realm and Netflix. Thanks to everyone who joined us this evening! There were great talks and important announcements, and the good news is that all of … <a href=\"https://blog.jetbrains.com/kotlin/2016/06/kotlin-night-recordings/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Meet the Kotlin Team at Gradle Summit",
                    "You’ve probably heard the news announcing that you’ll soon be able to write your Gradle build scripts and plugins in Kotlin. At the Kotlin Night in San Francisco, Hans Dockter from Gradle demoed the first prototype of the support. After … <a href=\"https://blog.jetbrains.com/kotlin/2016/06/meet-the-kotlin-team-at-gradle-summit/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin Eclipse Plugin 0.7 Is Here!",
                    "We are happy to present a new release of our plugin for Eclipse IDE. Along with the support for Kotlin 1.0.2 compiler, this update brings very important features and improvements. The code formatting feature was rebuilt in this release. Instead … <a href=\"https://blog.jetbrains.com/kotlin/2016/06/kotlin-eclipse-plugin-0-7-is-here/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Gradle Meets Kotlin",
                    "Back at JavaOne 2015, during a lunch break we started chatting with Hans Dockter, CEO of Gradle. A couple of days after the conference, a few of us were at the Gradle offices talking about what would be the beginning … <a href=\"https://blog.jetbrains.com/kotlin/2016/05/gradle-meets-kotlin/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin 1.0.2 is Here",
                    "We’re happy to announce the release of Kotlin 1.0.2, the second bugfix and tooling update for Kotlin. In addition to compiler and language bugfixes, Kotlin 1.0.2 adds a number of major features to the IDE and the build tools. Incremental … <a href=\"https://blog.jetbrains.com/kotlin/2016/05/kotlin-1-0-2-is-here/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin Post-1.0 Roadmap",
                    "It’s been almost two months since Kotlin 1.0 was released, and the team is now switching from stabilisation and bug fixes to new feature work, so it’s a great time to talk about our plans for the future. We’ve already … <a href=\"https://blog.jetbrains.com/kotlin/2016/04/kotlin-post-1-0-roadmap/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Kotlin Digest 2016.Q1",
                    "Welcome the Kotlin Digest of Q1 2016. It was in this quarter that Kotlin 1.0 was released, and naturally the number of articles and coverage received has substantially increased. We want to thank everyone for their contributions, and highlight some … <a href=\"https://blog.jetbrains.com/kotlin/2016/04/kotlin-digest-2016-q1/\">Continue reading <span class=\"meta-nav\">→</span></a>")
            ),
            actual.toTypedArray())
    }

    @Test fun `sync items for jetbrains blog`() {
        var actual = emptyList<RssItem>()
        syncRssWithWeb(
            loadRss = { CompletableFuture.completedFuture(loadResource("jetbrains.xml")) },
            saveToRepo = { actual = it })

        assertArrayEquals(
            arrayOf(
                RssItem(
                    "Meet JetBrains at GDC Europe 2016",
                    "Cologne, Germany welcomes the world famous Game Development Conference again this August. In just 10 days hundreds of professionals from the gaming industry will meet at the biggest European event in this area. JetBrains will join with ReSharper, Rider, CLion … <a href=\"https://blog.jetbrains.com/blog/2016/08/05/meet-jetbrains-at-gdc-europe-2016/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Introducing JetBrains Toolbox App",
                    "Last summer we held our third annual two-day hackathon, an event where anyone and everyone from JetBrains (as well as a few external contributors) gathered to work on a novel, high-impact idea. The JetBrains App Launcher was one of these … <a href=\"https://blog.jetbrains.com/blog/2016/05/25/introducing-jetbrains-toolbox-app/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Issue Tracking Tools Survey",
                    "Dear Developers and Development Teams, We want to know what you think! We are conducting a study to learn more about the Issue Tracking tools that professionals use in their work process, and would very much appreciate your input. As … <a href=\"https://blog.jetbrains.com/blog/2016/05/17/issue-tracking-tools-survey/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Security update for IntelliJ-based IDEs v2016.1 and older versions",
                    "We have just released an important update for all IntelliJ-based IDEs. This update addresses critical security vulnerabilities inside the underlying IntelliJ Platform. The vulnerabilities, in various forms, are also present in older versions of the IDEs; therefore, patches for those … <a href=\"https://blog.jetbrains.com/blog/2016/05/11/security-update-for-intellij-based-ides-v2016-1-and-older-versions/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "JetBrains Toolbox 2016.1 release is complete",
                    "We usually say a release is ‘available’ but ‘complete’ seems more appropriate for 2016.1. It took a whole month and many teams working together to publish all of the 2016.1 updates inside of JetBrains Toolbox. Here’s a short recap of … <a href=\"https://blog.jetbrains.com/blog/2016/04/28/jetbrains-toolbox-2016-1-release-is-complete/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "JetBrains Toolbox—Release and Versioning Changes",
                    "With the shift to subscriptions, one of our goals was to move away from the one major release per year model, focusing on continuously delivering value independently of versioning. On changing to this model, a question that came up was, … <a href=\"https://blog.jetbrains.com/blog/2016/03/09/jetbrains-toolbox-release-and-versioning-changes/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Mobile app development survey",
                    "As mobile development is growing and a lot of JetBrains tools get increasingly exposed to mobile developers, we are looking to adapt and ensure the best experience. However, most of us are not particularly experienced in mobile development tech, which … <a href=\"https://blog.jetbrains.com/blog/2016/02/22/mobile-app-development-survey/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "JetBrains Student Program: How to Renew & Graduation Discount",
                    "It’s been a bit more than a year since we first launched our free Student License Program, and today we are really pleased that more than 230,000 students worldwide have made the choice to use JetBrains tools for educational purposes. … <a href=\"https://blog.jetbrains.com/blog/2015/12/11/jetbrains-student-program-how-to-renew-graduation-discount/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "The Drive To Develop",
                    "In 2000, three friends set out to make their work easier by creating a tool that would remove mundane and somewhat inefficient tasks. This tool was called Renamer, and it had one job which was to rename things, a process … <a href=\"https://blog.jetbrains.com/blog/2015/12/10/the-drive-to-develop/\">Continue reading <span class=\"meta-nav\">→</span></a>"),
                RssItem(
                    "Meet Us at Apps World in London, November 18-19th",
                    "The Apps World event returns to Excel, London. A leading multi-platform event in the app industry, Apps World features 350 exhibitors and is expected to attract 10,000+ attendees. With 52% of Apps World attendees being developers, this is the place … <a href=\"https://blog.jetbrains.com/blog/2015/11/06/meet-us-at-apps-world-in-london/\">Continue reading <span class=\"meta-nav\">→</span></a>")),
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
        val actual = getRssItems({ CompletableFuture.completedFuture(Unit) }, { expected }).getLast()

        assertArrayEquals(
            expected.toTypedArray(),
            actual.toTypedArray())
    }
}