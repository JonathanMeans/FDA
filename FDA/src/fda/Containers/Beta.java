package fda.Containers;

import java.util.Arrays;

/**
 * This class represents a FanFiction Beta Reader.
 * Author: Karolina Reitz
 */
public class Beta implements Comparable<Beta> {
	private String name = "";
	private String betaUrl = "";
	private String betaId = "";
	private String joinDate = null;
	private String profile = "";
	private String[] genres = null;
	private int numStories = 0;
	private double popularity = 0.0;
	
	public Beta() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBetaUrl() {
		return betaUrl;
	}

	public void setBetaUrl(String betaUrl) {
		this.betaUrl = betaUrl;
	}

	public String getBetaId() {
		return betaId;
	}

	public void setBetaId(String betaId) {
		this.betaId = betaId;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public int getNumStories() {
		return numStories;
	}

	public void setNumStories(int numStories) {
		this.numStories = numStories;
	}

	public double getPopularity() {
		return popularity;
	}
	
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	@Override
	public int compareTo(Beta o) {
		// Reverse sort by priority (highest first)
		return -1 * Double.compare(getPopularity(), o.getPopularity());
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID: " + getBetaId() + "\n");
		buffer.append("Name: " + getName() + "\n");
		buffer.append("Num Stories: " + getNumStories() + "\n");
		buffer.append("Join Date: " + getJoinDate() + "\n");
		buffer.append("Popularity: " + getPopularity() + "\n");
		buffer.append("Profile: " + getProfile() + "\n");
		buffer.append("Genres: " + Arrays.toString(getGenres()));
		
		return buffer.toString();
	}
}
