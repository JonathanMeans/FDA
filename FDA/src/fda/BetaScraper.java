package fda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class scrapes Beta Reader information from FanFiction.net and creates
 * Beta objects.
 * Author: Karolina Reitz
 */
public class BetaScraper extends Scraper {
	public static final String FANFICTION_BASE_URL = "https://www.fanfiction.net/";
	public static final int DEFAULT_PAGES_TO_DOWNLOAD = 1;

	public BetaScraper() {

	}

	/**
	 * Main entry point - given a url to a Beta readers page on fanfiction.net,
	 * this will download the beta information, parse it into Beta objects, and
	 * return a List of the Beta objects.  This will download the default number
	 * of beta reader pages for the given URL (default is 1).
	 * 
	 * @param url The URL for the Beta readers page on fanfiction.net
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
	 * @param url The URL for the Beta readers page on fanfiction.net
	 * @param numPages The number of Beta pages to download
	 * @return a List of the parsed Beta objects
	 * @throws IOException
	 */
	public static List<Beta> extractBetas(String url, int numPages)
			throws IOException {
		List<Beta> betas = new ArrayList<Beta>();

		// Parse the given number of beta reader pages
		for (int i = 1; i <= numPages; i++) {
			parseBetasPage(url, i, betas);
		}

		return betas;
	}

	/*
	 * This function parses a single beta readers page and stores the Beta objects in the given List.
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
			beta.setBetaUrl(FANFICTION_BASE_URL + betaHref);
			
			String[] tokens = betaHref.split("\\/");
			if(tokens.length < 3) {
				System.err.println("Error parsing beta URL");
				return;
			}
			beta.setBetaId(tokens[2]);
			beta.setName(betaLink.ownText());
			Elements spans = betaLink.select("span[title=stories]");
			if(spans.size() == 0) {
				System.err.println("Error parsing numStories for the Beta reader");
				return;
			}
			beta.setNumStories(Integer.parseInt(spans.get(0).ownText()));
			spans = betaLink.select("span[title=join date]");
			beta.setJoinDate(spans.get(0).ownText());
			
			// Download the beta's profile page to get more information
			downloadProfile(beta);
			betas.add(beta);
		}
	}

	/*
	 * This function builds the URL for the given page number with the given base URL.
	 */
	private static String getPageUrl(String baseUrl, int pageNum) {
		if (pageNum == 1) {
			return baseUrl;
		} else {
			return baseUrl + "?&ppage=" + pageNum;
		}
	}

	/*
	 * This function downloads the given Beta's profile and adds the information to the object.
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

	public static void main(String[] args) {
		try {
			List<Beta> betas = extractBetas("https://www.fanfiction.net/betareaders/book/Harry-Potter/", 1);
			int index = 0;
			for (Beta beta : betas) {
				index++;
				System.out.println(index + ": " + beta.toString());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
