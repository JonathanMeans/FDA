package fda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

/**
 * Subclass of Scraper
 * Contains method specific to scraping relevant information from fandom pages
 * Author: Jonathan Means
 */
public class FandomScraper extends Scraper {


    //This should never be constructed
    private FandomScraper() {

    }

    //Master method
    //Return a semi-ordered array of fics, given a url and day-boundary
    public static Fanfic[] extractFics(String url, int days) throws IOException {

        //Create Date to stand for creation of site
        //Using the deprecated Date(int, int, int) constructor gives me a year in 3989 or something
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(1998, Calendar.OCTOBER, 15);
        Date siteOriginDate = calendar.getTime();

        long siteOriginTimeStamp = siteOriginDate.getTime();

        ProgressDialog dialog = new ProgressDialog();
        Document doc = Jsoup.connect(url).get();
        int numPages = countPages(doc);
        int currentPage = 1;

        //find a number between 10 and 100 that's kinda correlated to how far back we're searching
        int numTopFics = Math.min(100, Math.max(10, days));

        List<Fanfic> ficList = new ArrayList<Fanfic>();
        BoundedSortedFics topFicList = new BoundedSortedFics(numTopFics);

        long startTimeStamp = new Date().getTime();


        long endTimeStamp;
        if (days == Integer.MAX_VALUE) {
            endTimeStamp = siteOriginTimeStamp; //technically, no, but it'll work all the same
        } else {
            //force the calculation to use long to prevent overflow
            long deltaMillis = days * 24 * 60 * 60;
            deltaMillis *= 1000;
            endTimeStamp = startTimeStamp - deltaMillis;
        }

        Date boundaryDate = new Date(endTimeStamp);

        boolean datePassed = false;

        do {
            Elements stories = doc.select("div.z-list.zhover.zpointer");
            for (Element story : stories) {
                Fanfic fic = extractFicData(story);

                int progressValue = (int) (100 * (startTimeStamp - fic.getUpdatedDate().getTime())
                        / (startTimeStamp - endTimeStamp));
                dialog.setValue(progressValue);

                if (fic.getUpdatedDate().before(boundaryDate)) {
                    datePassed = true;
                    break;
                }

                if (!topFicList.insert(fic)) {
                    ficList.add(fic);
                }

            }

            url = incrementPage(url, currentPage);

            //sometimes there's a timeout error, so loop here
            doc = null;
            while (doc == null) {
                try {
                    doc = Jsoup.connect(url).get();
                } catch (SocketTimeoutException e) {
                    //Just wait
                }
            }
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

        dialog.dispose();
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

        //sometimes authors get deleted
        if (authorElement != null) {
            if (authorElement.attr("href").substring(0, 2).equals("/s")) {
                //multi-chapter fic. Ignore next link
                authorElement = story.select("a + a + a").first();
            }

            fic.setAuthor(authorElement.text());
            fic.setAuthorUrl(BASE_URL + authorElement.attr("href"));
        } else {
            fic.setAuthor("[deleted]");
            fic.setAuthorUrl("");
        }

    }

    private static void extractSummary(Element story, Fanfic fic) {
        String summary = story.select("div.z-indent.z-padtop").text();
        int cutoffIndex = summary.indexOf("Rated: "); //there's probably some summary out there somewhere
            //that will break this, but we can work with this for now.
            //And let's be honest, if they have redundant info like this in the summary, it's -probably-
            //not making the "Top n" list in the first place
        summary = summary.substring(0, cutoffIndex);
        fic.setSummary(summary);
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
