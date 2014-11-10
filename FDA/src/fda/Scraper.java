package fda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/*
 * This class is for extracting relevant information from fanfiction.net
 */
public class Scraper {

    public static Elements findFicData(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements stories = doc.select("div.z-list.zhover.zpointer");

        if (stories.isEmpty()) {
            throw new IOException("URL does not lead to a valid page.");
        }

        for (Element story : stories) {
            Fanfic fic = extractFicData(story);
        }

        return stories;
    }

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
        fic.setFicUrl(FDA.BASE_URL + titleElement.attr("href"));
    }

    private static void extractAuthorData(Element story, Fanfic fic) {
        Element authorElement = story.select("a + a").first();

        if (authorElement.attr("href").substring(0, 2).equals("/s")) {
            //multi-chapter fic. Ignore next link
            authorElement = story.select("a + a + a").first();
        }

        fic.setAuthor(authorElement.text());
        fic.setAuthorUrl(FDA.BASE_URL + authorElement.attr("href"));
    }

    private static void extractSummary(Element story, Fanfic fic) {
        fic.setSummary(story.select("div.z-indent.z-padtop").text());
    }

    private static void extractMisc(Element story, Fanfic fic) {
        String[] properties = story.select("div.z-padtop2.xgray").text().split(" - ");

        //calculate important numbers
        int ratingLength = "Rated: ".length();
        int chapterLength = "Chapters: ".length();
        int wordLength = "Words: ".length();
        int reviewsLength = "Reviews: ".length();
        int favsLength = "Favs: ".length();
        int followsLength = "Follows: ".length();

        //set obvious fields
        fic.setRating(properties[0].substring(ratingLength));
        fic.setLanguage(properties[1]);

        //Apparently genre is option
        //This mess handles that
        if (properties[2].startsWith("Chapters: ")) {
            String[] newProperties = new String[properties.length + 1];
            System.arraycopy(properties, 0, newProperties, 0, 2);
            newProperties[2] = null;
            fic.setGenres(null);
            System.arraycopy(properties, 2, newProperties, 3, properties.length - 2);
            properties = newProperties;
        } else {
            fic.setGenres(properties[2].split("/"));
        }

        //I think we can assume there won't be anything longer than 999 chapters
        fic.setChapters(Integer.parseInt(properties[3].substring(chapterLength)));

        //This requires work to remove the comma
        String wordCount = removeCommas(properties[4].substring(wordLength));
        fic.setWords(Integer.parseInt(wordCount));

        //The fields below need to be checked more carefully
        if (reviewsLength <= properties[5].length() && properties[5].substring(0, reviewsLength).equals("Reviews: ")) {
            String reviews = removeCommas(properties[5].substring(reviewsLength));
            fic.setReviews(Integer.parseInt(reviews));
        } else if (favsLength <= properties[5].length() && properties[5].substring(0, favsLength).equals("Favs: ")) {
            String favs = removeCommas(properties[5].substring(favsLength));
            fic.setFavorites(Integer.parseInt(favs));
        } else if (followsLength <= properties[5].length() && properties[5].substring(0, followsLength).equals("Follows: ")) {
            String follows = removeCommas(properties[5].substring(followsLength));
            fic.setFollows(Integer.parseInt(follows));
        }

        if (properties.length > 6) {
            if (favsLength <= properties[6].length() && properties[6].substring(0, favsLength).equals("Favs: ")) {
                String favs = removeCommas(properties[6].substring(favsLength));
                fic.setFavorites(Integer.parseInt(favs));
            } else if (followsLength <= properties[6].length() && properties[6].substring(0, followsLength).equals("Follows: ")) {
                String follows = removeCommas(properties[6].substring(followsLength));
                fic.setFollows(Integer.parseInt(follows));
            }
        }

        if (properties.length > 7) {
            if (followsLength <= properties[7].length() && properties[7].substring(0, followsLength).equals("Follows: ")) {
                String follows = removeCommas(properties[7].substring(followsLength));
                fic.setFollows(Integer.parseInt(follows));
            }
        }

        String characterData = properties[properties.length - 1];
        if (characterData.equals("Complete")) {
            characterData = properties[properties.length - 2];
        }
        extractCharactersFromPropertyList(characterData, fic);
    }

    private static void extractCharactersFromPropertyList(String data, Fanfic fic) {
        if (data.contains("data-xutime")) { return; } //if there is a character named "data-xutime," I give up.
        String[] characters = new String[4];
        String[][] pairings = new String[2][2];

        String[] characterArray = data.split(", ");
        String character = null;
        if (characterArray[0].startsWith("[")) {
            character = characterArray[0].substring(1);
            characters[0] = character;
            pairings[0][0] = character;

            character = characterArray[1].substring(0, characterArray[1].length() - 1);
            characters[1] = character;
            pairings[0][1] = character;
        } else {
            characters[0] = characterArray[0];
        }

        if (characterArray.length > 1 && pairings[0][0] == null) {
            characters[1] = characterArray[1];
        }

        if (characterArray.length > 2 && characterArray[2].startsWith("[")) {
            character = characterArray[2].substring(1);
            int pairingsIndex = pairings[0][0] == null ? 0 : 1;
            characters[2] = character;
            pairings[pairingsIndex][0] = character;

            character = characterArray[3].substring(0, characterArray[3].length() - 1);
            characters[3] = character;
            pairings[pairingsIndex][1] = character;
        } else if (characterArray.length > 2) {
            characters[2] = characterArray[2];
        }

        if (characterArray.length > 3 && pairings[1][0] == null) {
            characters[3] = characterArray[3];
        }

        //set things to null, if needed
        if (characters[0] == null) { characters = null; }
        if (pairings[1][0] == null) { pairings[1] = null; }
        if (pairings[0][0] == null) { pairings = null; }

        fic.setCharacters(characters);
        fic.setPairings(pairings);

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

    //Helper method to remove commas from a string representation of a number
    private static String removeCommas(String number) {
        String[] numberArray = number.split(",");
        String result = "";
        for (String group : numberArray) {
            result += group;
        }
        return result;
    }

}
