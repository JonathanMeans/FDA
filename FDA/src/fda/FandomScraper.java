package fda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

/**
 * Subclass of Scraper
 * Contains method specific to scraping relevant information from fandom pages
 */
public class FandomScraper extends  Scraper {

    private static Fanfic extractFicData(Element story) {
        Fanfic fic = new Fanfic();
        extractTitleData(story, fic);
        extractAuthorData(story, fic);
        extractSummary(story, fic);
        extractMisc(story, fic);
        extractDates(story, fic);
        return new Fanfic();
    }

        private static void extractTitleData(Element story, Fanfic fic) {
        Element titleElement = story.select("a").first();
        fic.setTitle(titleElement.text());
        fic.setFicUrl(BASE_URL + titleElement.attr("href"));
    }

    private static void extractAuthorData(Element story, Fanfic fic) {
        Element authorElement = story.select("a + a").first();

        if (authorElement.attr("href").substring(0, 2).equals("/s")) {
            //multi-chapter fic. Ignore next link
            authorElement = story.select("a + a + a").first();
        }

        fic.setAuthor(authorElement.text());
        fic.setAuthorUrl(BASE_URL + authorElement.attr("href"));
    }

    private static void extractSummary(Element story, Fanfic fic) {
        fic.setSummary(story.select("div.z-indent.z-padtop").text());
    }

    private static void extractDates(Element story, Fanfic fic) {
        Elements publishedData = story.select("span");
        Iterator<Element> iterator = publishedData.iterator();
        String publishedString = iterator.next().attr("data-xutime");
        if (publishedString.equals("")) {
            publishedString = iterator.next().attr("data-xutime");
        }
        fic.setPublishedDate(new Date(Long.parseLong(publishedString) * 1000L));

        if (publishedData.size() < 3) {
            return;
        }

        //updated date may not always exist
        //easier way to tell is to check whether there's another span after the second
        //then update values accordingly
        fic.setUpdatedDate(new Date(Long.parseLong(publishedString) * 1000L));
        publishedString = iterator.next().attr("data-xutime");
        fic.setPublishedDate(new Date(Long.parseLong(publishedString) * 1000L));
    }

    private static int countPages(Document doc, String url) {
        Elements pageList = doc.select("center[style=margin-top:5px;margin-bottom:5px;]");

        //If you're trying to call this on a fandom with only one page, something's wrong
        //But we should handle it anyway
        if (pageList.isEmpty()) {
            return 1;
        }

        //Look for last page marker
        Elements links = pageList.first().getElementsByTag("a");
        for (Element link : links) {
            if (link.text().equals("Last")) {
                try {
                    String queries[] = new URL(BASE_URL + link.attr("href")).getQuery().split("\\&");
                    for (String query : queries) {
                        if (query.startsWith("p")) {
                            return Integer.parseInt(query.substring(2)); //discard "p="
                        }
                    }
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        //There are multiple pages, but there is no explicit "Last" link. Therefore, there are two pages
        return 2;

    }
}
