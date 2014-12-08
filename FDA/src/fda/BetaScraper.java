package fda;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class scrapes Beta Reader information from FanFiction.net and creates
 * Beta objects. Author: Karolina Reitz
 */
public class BetaScraper extends Scraper implements PropertyChangeListener {
	public static final String FANFICTION_BASE_URL = "https://www.fanfiction.net/";
	public static final int DEFAULT_PAGES_TO_DOWNLOAD = 1;
	private static final int BETAS_PER_PAGE = 51;
	private static ProgressDialog progressDialog = null;
	private static int currentProgressValue = 0;
	private static int totalProgressTasks = 0;

	public BetaScraper() {

	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * Main entry point - given a url to a Beta readers page on fanfiction.net,
	 * this will download the beta information, parse it into Beta objects, and
	 * return a List of the Beta objects. This will download the default number
	 * of beta reader pages for the given URL (default is 1).
	 * 
	 * @param url
	 *            The URL for the Beta readers page on fanfiction.net
	 * @return a List of the parsed Beta objects
	 * @throws IOException
	 */
	public static List<Beta> extractBetas(String url) throws IOException {
		return extractBetas(url, DEFAULT_PAGES_TO_DOWNLOAD);
	}

	/**
	 * Main entry point - given a url to a Beta readers page on fanfiction.net,
	 * this will download the beta information, parse it into Beta objects, and
	 * return a List of the Beta objects.
	 * 
	 * @param url
	 *            The URL for the Beta readers page on fanfiction.net
	 * @param numPages
	 *            The number of Beta pages to download
	 * @return a List of the parsed Beta objects
	 * @throws IOException
	 */
	public static List<Beta> extractBetas(String url, int numPages)
			throws IOException {
		List<Beta> betas = new ArrayList<Beta>();

		// Create a progress bar dialog to communicate the progress to the user
		progressDialog = new ProgressDialog();
		currentProgressValue = 0;

		// Calculate the total number of tasks represented by the progress bar
		totalProgressTasks = numPages * BETAS_PER_PAGE;

		// Parse the given number of beta reader pages
		for (int i = 1; i <= numPages; i++) {
			// Parse the next beta readers page
			try {
				parseBetasPage(url, i, betas);
			} catch (IOException ioe) {
				// Clean up the progress bar
				progressDialog.dispose();
				throw ioe;
			}
		}

		// Sort the list of betas
		Collections.sort(betas);

		// Dispose of the progress bar dialog since we're done
		progressDialog.dispose();

		return betas;
	}

	/*
	 * This function parses a single beta readers page and stores the Beta
	 * objects in the given List.
	 */
	private static void parseBetasPage(String url, int pageNum, List<Beta> betas)
			throws IOException {
		String betasUrl = getPageUrl(url, pageNum);

		Document doc = Jsoup.connect(betasUrl).get();
		Elements pageList = doc
				.select("div[style=overflow: hidden;margin-bottom:5px;border-top:solid 1px #cdcdcd;]");
		Elements betaCells = pageList.first().getElementsByTag("td");
		for (Element betaCell : betaCells) {
			Element betaLink = betaCell.child(0);
			String betaHref = betaLink.attr("href");
			Beta beta = new Beta();
			beta.setBetaUrl(createAuthorUrl(betaHref.substring(1)));

			String[] tokens = betaHref.split("\\/");
			if (tokens.length < 3) {
				System.err.println("Error parsing beta URL");
				return;
			}
			beta.setBetaId(tokens[2]);
			beta.setName(betaLink.ownText());
			Elements spans = betaLink.select("span[title=stories]");
			if (spans.size() == 0) {
				System.err
						.println("Error parsing numStories for the Beta reader");
				return;
			}
			beta.setNumStories(Integer.parseInt(spans.get(0).ownText()));
			spans = betaLink.select("span[title=join date]");
			beta.setJoinDate(spans.get(0).ownText());

			// Download the beta's profile page to get more information
			// downloadProfile(beta);
			downloadAuthorPage(beta);
			betas.add(beta);

			// Update the progress bar
			currentProgressValue++;
			progressDialog.setValue((100 * currentProgressValue)
					/ totalProgressTasks);
			// System.out.println((100 * currentProgressValue) /
			// totalProgressTasks);
			// progressDialog.invalidate();
		}
	}

	private static String createAuthorUrl(String url) {
		return FANFICTION_BASE_URL + url.replace("beta", "u");
	}

	/*
	 * This function builds the URL for the given page number with the given
	 * base URL.
	 */
	private static String getPageUrl(String baseUrl, int pageNum) {
		if (pageNum == 1) {
			return baseUrl;
		} else {
			return baseUrl + "?&ppage=" + pageNum;
		}
	}

	/*
	 * This function downloads the given Beta's profile and adds the information
	 * to the object.
	 */
	private static void downloadProfile(Beta beta) throws IOException {
		// Download the profile page
		Document doc = Jsoup.connect(beta.getBetaUrl()).get();

		// Pull out the beta's profile
		Elements tables = doc.select("table[id=gui_table1i");
		Elements profile = tables.get(0).getElementsByTag("blockquote");
		if (profile.size() > 0) {
			beta.setProfile(profile.first().ownText());
		}

		// Retrieve the genres that the beta will read for
		tables = doc.select("table[id=gui_table2i]");
		Elements innerRows = tables.get(0).child(0).children();
		if (innerRows.size() < 4) {
			System.err.println("Error parsing profile page");
			return;
		}
		Elements table = innerRows.get(3).getElementsByTag("table");
		if (table.size() == 0) {
			System.err.println("Error parsing genre table on profile page.");
		}

		// Find the genres row in the table
		Elements genres = table.get(0).getElementsByTag("td");
		String[] genresArr = new String[genres.size()];
		for (int i = 0; i < genres.size(); i++) {
			// Get the genre text
			String genre = genres.get(i).ownText();
			;
			if (genre.equals("")) {
				// Check if this is a genre that the beta is willing to read for
				// but hasn't yet
				if (genres.get(i).childNodeSize() > 0) {
					genre = genres.get(i).child(0).ownText();
				}
			}
			genresArr[i] = genre;
		}
		beta.setGenres(genresArr);
	}

	private static void downloadAuthorPage(Beta beta) throws IOException {
		// Download the profile page
		// System.out.println("Downloading " + beta.getBetaUrl());
		Document doc = null;
		// try {
		doc = Jsoup.connect(beta.getBetaUrl()).get();
		// } catch (IOException ioe) {
		// // Try one more time to see if this was just a network error
		// doc = Jsoup.connect(beta.getBetaUrl()).get();
		// }

		// Pull out the beta's profile
		double popularitySum = 0.0;
		Elements stories = doc.select("div[class=z-padtop2 xgray");
		for (int i = 0; i < stories.size(); i++) {
			Element story = stories.get(i);
			Fanfic fic = createFic(story);
			popularitySum += fic.getPopularity();
		}

		// A beta's popularity is the average popularity of their stories
		double popularity = popularitySum / (double) stories.size();
		beta.setPopularity(popularity);
	}

	private static Fanfic createFic(Element story) {
		Fanfic fic = new Fanfic();

		String text = story.ownText();

		int favsPos = text.indexOf("Favs: ");
		if (favsPos >= 0) {
			String favsText = text
					.substring(favsPos + 6, text.indexOf("-", favsPos + 1))
					.replaceAll(",", "").trim();
			try {
				fic.setFavorites(Integer.parseInt(favsText));
			} catch (NumberFormatException nfe) {
				System.err.println("***** Error parsing input: '" + text + "'");
				nfe.printStackTrace();
			}
		}

		Elements publishedData = story.select("span");

		if (publishedData.size() == 2) {
			// The first is the updated date, the second is the published date
			String publishedString = publishedData.get(0).attr("data-xutime");
			fic.setUpdatedDate(new Date(Long.parseLong(publishedString) * 1000L));
		}

		// Now extract the published date
		String publishedString = publishedData.get(publishedData.size() - 1)
				.attr("data-xutime");
		fic.setPublishedDate(new Date(Long.parseLong(publishedString) * 1000L));

		// If there's no updated date, set the updated date to be the published
		// date
		if (fic.getUpdatedDate() == null) {
			fic.setUpdatedDate(fic.getPublishedDate());
		}

		return fic;
	}

	public static void test(String url) throws Exception {
		String out = new Scanner(new URL(url).openStream(), "UTF-8")
				.useDelimiter("\\A").next();
		System.out.println(out);
	}

	public static void main(String[] args) {
		try {
			List<Beta> betas = extractBetas(
					"https://www.fanfiction.net/betareaders/book/Harry-Potter/",
					3);
			int index = 0;
			for (Beta beta : betas) {
				index++;
				System.out.println(index + ": " + beta.getName()
						+ " popularity = " + beta.getPopularity()
						+ " numStories = " + beta.getNumStories());
			}
			// test("https://www.fanfiction.net/betareaders/book/Harry-Potter/");
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}
