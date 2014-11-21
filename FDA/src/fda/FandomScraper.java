package fda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Subclass of Scraper
 * Contains method specific to scraping relevant information from fandom pages
 */
public class FandomScraper extends Scraper {

    //This should never be constructed
    private FandomScraper() {

    }

    //Master method
    //Return a semi-ordered array of fics, given a url and day-boundary
    public static Fanfic[] extractFics(String url, int days) throws IOException {
        Document doc = Jsoup.connect(url).get();
        int numPages = countPages(doc);
        int currentPage = 1;

        //find a number between 10 and 100 that's kinda correlated to how far back we're searching
        int numTopFics = Math.min(100, Math.max(10, days));

        List<Fanfic> ficList = new ArrayList<Fanfic>();
        BoundedSortedFics topFicList = new BoundedSortedFics(numTopFics);

        long timestamp = new Date().getTime();// - days * 24 * 60 * 60 * 1000;
        if (days == Integer.MAX_VALUE) {
            timestamp = 0; //technically, no, but it'll work all the same
        }
        Date boundaryDate = new Date(timestamp);

        boolean datePassed = false;

        do {
            Elements stories = doc.select("div.z-list.zhover.zpointer");
            for (Element story : stories) {
                Fanfic fic = extractFicData(story);

                if (fic.getUpdatedDate().before(boundaryDate)) {
                    datePassed = true;
                    break;
                }

                if (!topFicList.insert(fic)) {
                    ficList.add(fic);
                }

            }

            url = incrementPage(url, currentPage);
            doc = Jsoup.connect(url).get();
            currentPage++;
        } while (!datePassed && currentPage <= numPages);

        Fanfic[] topFicArray = new Fanfic[topFicList.size() + 1 + ficList.size()];
        for (int i = 0; i < topFicList.size(); ++i) {
            topFicArray[i] = topFicList.get(i);
        }

        topFicArray[topFicList.size()] = null;

        int i = topFicList.size() + 1;

        for (Fanfic fic : ficList) {
            topFicArray[i] = fic;
            ++i;
        }

        return topFicArray;
    }

    private static String incrementPage(String url, int pageNumber) throws MalformedURLException {
        if (pageNumber == 1) {
            if (new URL(url).getQuery() == null) {
                if (!url.endsWith("/")) {
                    url = url + "/";
                }
                return url + "?p=2";
            }
            return url + "&p=2";
        }

        return url.substring(0, url.length() - Integer.toString(pageNumber).length()) + Integer.toString(pageNumber + 1);
    }

    private static Fanfic extractFicData(Element story) {
        Fanfic fic = new Fanfic();
        extractTitleData(story, fic);
        extractAuthorData(story, fic);
        extractSummary(story, fic);
        extractMisc(story, fic);
        extractDates(story, fic);
        return fic;
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
        fic.setUpdatedDate(new Date(Long.parseLong(publishedString) * 1000L));

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

    private static int countPages(Document doc) {
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
