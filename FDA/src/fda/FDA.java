/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fda;

import org.jfree.chart.demo.BarChartDemo1;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author paulcorter
 */
public class FDA {

    //Used for URL validation
    public enum UrlType{FANDOM, FIC, BETA, UNKNOWN}
    public static final String BASE_URL = "https://www.fanfiction.net";

    /* Take String representation of a URL and determine what it is meant to lead to
     *
     * @param url the url to be tested
     * @return the corresponding UrlType
     */
    public static UrlType checkUrl(String url) {
        String ficString = BASE_URL + "/s/\\d+/\\d+/.+";
        String betaString = BASE_URL + "/betareaders/.+";
        String fandomString = BASE_URL + "/.+";

        //Test whether URL matches the fic pattern
        Pattern storyPattern = Pattern.compile(ficString);
        Matcher storyMatcher = storyPattern.matcher(url);
        if (storyMatcher.find()) {
            return UrlType.FIC;
        }

        //Test whether URL matches the beta listing pattern
        Pattern betaPattern = Pattern.compile(betaString);
        Matcher betaMatcher = betaPattern.matcher(url);
        if (betaMatcher.find()) { return UrlType.BETA; }

        //Test whether URL matches the fandom pattern
        Pattern fandomPattern = Pattern.compile(fandomString);
        Matcher fandomMatcher = fandomPattern.matcher(url);
        if (fandomMatcher.find()) { return UrlType.FANDOM; }

        return UrlType.UNKNOWN;

    }

    /* Added comment to test pushing to the Git repository */
    public static void main(String[] args) {
        System.out.println("Hello Worlds!");
        try {
            Scraper.findFicData("https://www.fanfiction.net/book/Harry-Potter");
        } catch (IOException e) {
            System.out.println("Could not connect to fanfiction.net");
        }
    }
    
}
