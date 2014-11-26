package fda;

import org.jsoup.nodes.Element;
import java.util.*;

/*
 * This class is for extracting relevant information from fanfiction.net
 * Static methods only
 * Should never be instantiated
 * Author: Jonathan Means
 */
public class Scraper {

    public static final String BASE_URL = "https://www.fanfiction.net";

    /*
     * This methods populates the following fields of the Fanfic class:
     * Rating
     * Chapters
     * Words
     * reviews
     * favorites
     * follows
     * characters
     * pairings
     */
    protected static void extractMisc(Element story, Fanfic fic) {
        String[] properties = story.select("div.z-padtop2.xgray").text().split(" - ");
        List<String> propertyList = new ArrayList<String>(Arrays.asList(properties));
        Iterator<String> iterator = propertyList.iterator();

        //calculate important numbers
        int ratingLength = "Rated: ".length();
        int chapterLength = "Chapters: ".length();
        int wordLength = "Words: ".length();
        int reviewsLength = "Reviews: ".length();
        int favsLength = "Favs: ".length();
        int followsLength = "Follows: ".length();

        //set obvious fields
        fic.setRating(iterator.next().substring(ratingLength));
        fic.setLanguage(iterator.next());

        //Apparently genre is option
        //This mess handles that
        //Before the 'if', it is unknown whether the String genres contains the genres or the chapter count
        String genres = iterator.next();
        String chapters;
        if (!genres.startsWith("Chapters: ")) {
            fic.setGenres(genres.split("/")); //genres contains the right information. Split it and proceed
            chapters = iterator.next();
        } else {
            //the fic has no genres. Discard, and set the chapters String
            chapters = genres;
        }

        //I think we can assume there won't be anything longer than 999 chapters
        //But why risk it?
        String chapterCount = removeCommas(chapters.substring(chapterLength));
        fic.setChapters(Integer.parseInt(chapterCount));

        String wordCount = removeCommas(iterator.next().substring(wordLength));
        fic.setWords(Integer.parseInt(wordCount));

        //Fics may not have favorites, follows, or reviews, which makes this a bit more involved
        //Iterator -should- be incremented in a way to handle it, though
        String reviews = iterator.next();
        String favs;
        String follows = null;

        //We know there is at least a published date left in the properties, so no need to check whether reviews is null
        if (reviewsLength <= reviews.length() && reviews.substring(0, reviewsLength).equals("Reviews: ")) {
            String reviewCount = removeCommas(reviews.substring(reviewsLength));
            fic.setReviews(Integer.parseInt(reviewCount));
            favs = iterator.next();
        } else  {
            favs = reviews;
        }

        if (favs != null) {
            if (favsLength <= favs.length() && favs.substring(0, favsLength).equals("Favs: ")) {
                String favsCount = removeCommas(favs.substring(favsLength));
                fic.setFavorites(Integer.parseInt(favsCount));
                follows = iterator.next();
            } else {
                follows = favs;
            }
        }

        if (follows != null) {
            if (followsLength <= follows.length() && follows.substring(0, followsLength).equals("Follows: ")) {
                String followsCount = removeCommas(follows.substring(followsLength));
                fic.setFollows(Integer.parseInt(followsCount));
            }
        }

        //If the fic gives the characters, they will either be listed last or second-to-last
        String characterData = properties[properties.length - 1];
        if (characterData.equals("Complete")) {
            characterData = properties[properties.length - 2];
        }

        //This just gets its own method because it's so large
        extractCharactersFromPropertyList(characterData, fic);
    }

    //This method finds the characters and pairings of a fic
    private static void extractCharactersFromPropertyList(String data, Fanfic fic) {
        //Make sure there -are- actually characters
        if (data.contains("Published: ")) { return; } //if there is a character named "Published: " I give up.
        String[] characters = new String[4];
        String[][] pairings = new String[2][2];

        //pairings are not separated with commas, so we'll have to handle that, and it'll be ugly. Yay
        String[] characterArray = data.split("[\\,\\]]");
        String character;

        //If there are pairings, they will always come first
        //Remove brackets and insert characters and pairings to proper arrays
        //There's probably a more general way to do this, but with only four possible characters
        //it's not worth bothering with at the moment
        if (characterArray[0].startsWith("[")) {
            character = characterArray[0].substring(1);
            characters[0] = character;
            pairings[0][0] = character;


            character = characterArray[1];
            characters[1] = character;
            pairings[0][1] = character;
        } else {
            characters[0] = characterArray[0];
        }

        if (characterArray.length > 1 && pairings[0][0] == null) {
            characters[1] = characterArray[1];
        }

        //I think there's still a bug in this block
        //TODO: find and destroy
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
