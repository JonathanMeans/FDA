package fda;

import java.util.Date;

/**
 * This class represents a fic
 * Methods include setters and getters for each field
 * Author: Jonathan Means
 */
public class Fanfic implements Comparable<Fanfic> {
    private String title;
    private String ficUrl;
    private String author;
    private String authorUrl;
    private String summary;
    private String rating;
    private String language;
    private String[] genres;
    private int chapters;
    private int words;
    private int favorites;

    public String getTitle() {
        return title;
    }

    public String getFicUrl() {
        return ficUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String getRating() {
        return rating;
    }

    public String getLanguage() {
        return language;
    }

    public String[] getGenres() {
        return genres;
    }

    public int getChapters() {
        return chapters;
    }

    public int getWords() {
        return words;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getFollows() {
        return follows;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String[] getCharacters() {
        return characters;
    }

    public String[][] getPairings() {
        return pairings;
    }

    private int reviews;
    private int follows;
    private Date publishedDate;
    private Date updatedDate;
    private String[] characters;
    private String[][] pairings;
    private double popularity;

    public Fanfic() {
        characters = new String[1];
        characters[0] = "None";

        genres = new String[1];
        genres[0] = "None";
    }

    public void setPairings(String[][] pairings) {
        this.pairings = pairings;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFicUrl(String ficUrl) {
        this.ficUrl = ficUrl;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    public int getReviews() {
        return reviews;
    }

    public double getPopularity() {
        if (popularity == 0) {
            long elapsedTime = (new Date().getTime() - updatedDate.getTime()) / 1000 / 60 / 60 / 24 + 2;
            elapsedTime = Math.min(elapsedTime, 30);
            popularity = favorites / Math.log(elapsedTime / 1000 + 2); //extra thousand for scaling
            //2 is added to pre-emptively prevent division by 0
        }
        return  popularity;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public int compareTo(Fanfic fic) {
        return (int) Math.signum(this.getPopularity() - fic.getPopularity());
    }

    public String toString() {
        return title + " by " + author + " | " + getPopularity();
    }
}
