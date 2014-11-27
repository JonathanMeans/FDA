package fda;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;

/**
 * class for constructing necessary charts
 * Author: Jonathan Means
 */
public class FicChartFactory {
    private Fanfic[] fics;

    public enum Preference {WRITER, READER}

    public enum ChartedAttribute {CHARACTER, PAIRING, GENRE, WORDS, RATING}

    //Creating charts takes time, so we're going to have a separate field for each.
    //Then, if the chart already exists, we can just return it.
    private JFreeChart readerPreferredCharacterChart;
    private JFreeChart readerPreferredPairingChart;
    private JFreeChart readerPreferredGenreChart;
    private JFreeChart readerPreferredWordsChart;
    private JFreeChart readerPreferredRatingChart;

    private JFreeChart writerPreferredCharacterChart;
    private JFreeChart writerPreferredPairingChart;
    private JFreeChart writerPreferredGenreChart;
    private JFreeChart writerPreferredWordsChart;
    private JFreeChart writerPreferredRatingChart;

    public FicChartFactory(Fanfic[] fics) {
        this.fics = fics;
    }

    public ChartPanel defaultPanel() {
        return createPanel(Preference.WRITER, ChartedAttribute.RATING);
    }

    //I am ashamed of the redundancy of this code. I'm certain there's a way to refactor it,
    //but I have no ideas at the moment.
    //Anyway, this method uses criminal amounts of boilerplate to create the chart specified by the arguments
    public ChartPanel createPanel(Preference preference, ChartedAttribute attribute) {
        if (preference == Preference.READER) {
            if (attribute == ChartedAttribute.CHARACTER) {
                if (readerPreferredCharacterChart != null) {
                    return new ChartPanel(readerPreferredCharacterChart);
                }

                CategoryDataset dataset = createCharacterDataSet(preference);
                readerPreferredCharacterChart = ChartFactory.createBarChart("Character popularity", "Character",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(readerPreferredCharacterChart);

            } else if (attribute == ChartedAttribute.PAIRING) {
                if (readerPreferredPairingChart != null) {
                    return new ChartPanel(readerPreferredCharacterChart);
                }

                CategoryDataset dataset = createPairingDataSet(preference);
                readerPreferredPairingChart = ChartFactory.createBarChart("Pairing popularity", "Pairing",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(readerPreferredPairingChart);

            } else if (attribute == ChartedAttribute.GENRE) {
                if (readerPreferredGenreChart != null) {
                    return new ChartPanel(readerPreferredGenreChart);
                }

                CategoryDataset dataset = createGenreDataSet(preference);
                readerPreferredGenreChart = ChartFactory.createBarChart("Genre popularity", "Genre",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(readerPreferredGenreChart);
            } else if (attribute == ChartedAttribute.RATING) {
                if (readerPreferredRatingChart != null) {
                    return new ChartPanel(readerPreferredRatingChart);
                }

                CategoryDataset dataset = createRatingDataSet(preference);
                readerPreferredRatingChart = ChartFactory.createBarChart("Rating popularity", "Rating",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(readerPreferredRatingChart);
            }


        } else if (preference == Preference.WRITER) {
            if (attribute == ChartedAttribute.CHARACTER) {
                if (writerPreferredCharacterChart != null) {
                    return new ChartPanel(writerPreferredCharacterChart);
                }

                CategoryDataset dataset = createCharacterDataSet(preference);
                writerPreferredCharacterChart = ChartFactory.createBarChart("Character popularity", "Character",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(writerPreferredCharacterChart);

            } else if (attribute == ChartedAttribute.PAIRING) {
                if (writerPreferredPairingChart != null) {
                    return new ChartPanel(writerPreferredCharacterChart);
                }

                CategoryDataset dataset = createPairingDataSet(preference);
                writerPreferredPairingChart = ChartFactory.createBarChart("Pairing popularity", "Pairing",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(writerPreferredPairingChart);

            } else if (attribute == ChartedAttribute.GENRE) {
                if (writerPreferredGenreChart != null) {
                    return new ChartPanel(writerPreferredGenreChart);
                }

                CategoryDataset dataset = createGenreDataSet(preference);
                writerPreferredGenreChart = ChartFactory.createBarChart("Genre popularity", "Genre",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(writerPreferredGenreChart);

            } else if (attribute == ChartedAttribute.RATING) {
                if (writerPreferredRatingChart != null) {
                    return new ChartPanel(writerPreferredRatingChart);
                }

                CategoryDataset dataset = createRatingDataSet(preference);
                writerPreferredRatingChart = ChartFactory.createBarChart("Rating popularity", "Rating",
                        "Percentage of Popularity", dataset);
                return new ChartPanel(writerPreferredRatingChart);
            }
        }
        return null;
    }

    private CategoryDataset createCharacterDataSet(Preference preference) {
        Map<String, Double> characterMap = new HashMap<String, Double>();
        double totalPopularity = 0;

        if (preference == Preference.READER) {
            for (Fanfic fic : fics) {
                if (fic == null) {
                    break;
                }
                double popularityIncrement = fic.getPopularity();
                totalPopularity += popularityIncrement;

                for (String character : fic.getCharacters()) {
                    if (characterMap.get(character) == null) {
                        characterMap.put(character, popularityIncrement);
                    } else {
                        double currentPopularity = characterMap.get(character);
                        characterMap.put(character, currentPopularity + popularityIncrement);
                    }
                }
            }
        }

        //Finish iterating through the fics
        if (preference == Preference.WRITER) {
            double characterCount = 0;
            for (Fanfic fic : fics) {

                characterCount += 1;
                //this is duplicate code, but I'm not sure it's worth breaking into its own method....
                if (fic == null) {
                    continue;
                }

                for (String character : fic.getCharacters()) {
                    if (characterMap.get(character) == null) {
                        characterMap.put(character, 1.0);
                    } else {
                        double currentCount = characterMap.get(character);
                        characterMap.put(character, currentCount + 1.0);
                    }
                }
            }
            totalPopularity = characterCount;
        }

        return makeDataSetFromMap(characterMap, totalPopularity);

    }

    private CategoryDataset createPairingDataSet(Preference preference) {
        Map<String, Double> pairingMap = new HashMap<String, Double>();
        double totalPopularity = 0;

        if (preference == Preference.READER) {
            for (Fanfic fic : fics) {
                if (fic == null) {
                    break;
                }

                String[][] pairings = fic.getPairings();

                if (pairings == null) {
                    continue;
                }
                for (String[] pairing : pairings) {
                    String pairingString = normalizePairing(pairing);
                    if (pairingString == null) {
                        continue;
                    }

                    if (pairingMap.get(pairingString) == null) {
                        pairingMap.put(pairingString, fic.getPopularity());

                    } else {
                        double currentPopularity = pairingMap.get(pairingString);
                        pairingMap.put(pairingString, currentPopularity + fic.getPopularity());
                    }

                    totalPopularity += fic.getPopularity();
                }
            }
        } else {

            double totalCount = 0;
            for (Fanfic fic : fics) {
                if (fic == null) {
                    continue;
                }
                String[][] pairings = fic.getPairings();

                if (pairings == null) {
                    continue;
                }
                for (String[] pairing : pairings) {
                    String pairingString = normalizePairing(pairing);
                    if (pairingString == null) {
                        continue;
                    }

                    if (pairingMap.get(pairingString) == null) {
                        pairingMap.put(pairingString, 1.0);
                    } else {
                        double currentCount = pairingMap.get(pairingString);
                        pairingMap.put(pairingString, currentCount + 1);
                    }

                    totalCount += 1;
                }
            }
            totalPopularity = totalCount;
        }

        return makeDataSetFromMap(pairingMap, totalPopularity);
    }

    private CategoryDataset createGenreDataSet(Preference preference) {
        double totalPopularity = 0;
        Map<String, Double> genreMap = new HashMap<String, Double>();

        if (preference == Preference.READER) {
            for (Fanfic fic : fics) {
                if (fic == null) {
                    break;
                }

                String[] genres = fic.getGenres();

                if (genres == null) {
                    continue;
                }
                for (String genre : genres) {
                    if (genre == null) {
                        continue;
                    }

                    if (genreMap.get(genre) == null) {
                        genreMap.put(genre, fic.getPopularity());

                    } else {
                        double currentPopularity = genreMap.get(genre);
                        genreMap.put(genre, currentPopularity + fic.getPopularity());
                    }

                    totalPopularity += fic.getPopularity();
                }
            }
        } else {

            double totalCount = 0;
            for (Fanfic fic : fics) {
                if (fic == null) {
                    continue;
                }
                String[] genres = fic.getGenres();

                if (genres == null) {
                    continue;
                }

                for (String genre : genres) {

                    if (genreMap.get(genre) == null) {
                        genreMap.put(genre, 1.0);
                    } else {
                        double currentCount = genreMap.get(genre);
                        genreMap.put(genre, currentCount + 1);
                    }

                    totalCount += 1;
                }
            }
            totalPopularity = totalCount;
        }

        return makeDataSetFromMap(genreMap, totalPopularity);
    }

    private CategoryDataset createRatingDataSet(Preference preference) {
        Map<String, Double> ratingMap = new HashMap<String, Double>();
        double totalPopularity = 0;

        if (preference == Preference.READER) {
            for (Fanfic fic : fics) {
                if (fic == null) {
                    break;
                }

                double popularityIncrement = fic.getPopularity();
                totalPopularity += popularityIncrement;

                String rating = fic.getRating();
                if (rating == null) {
                    continue;
                }

                if (ratingMap.get(rating) == null) {
                    ratingMap.put(rating, popularityIncrement);
                } else {
                    double currentPopularity = ratingMap.get(rating);
                    ratingMap.put(rating, currentPopularity + popularityIncrement);
                }
            }
        }

        //Finish iterating through the fics
        if (preference == Preference.WRITER) {
            double ratingCount = 0;
            for (Fanfic fic : fics) {

                ratingCount += 1;
                //this is duplicate code, but I'm not sure it's worth breaking into its own method....
                if (fic == null) {
                    continue;
                }

                String rating = fic.getRating();
                if (ratingMap.get(rating) == null) {
                    ratingMap.put(rating, 1.0);
                } else {
                    double currentCount = ratingMap.get(rating);
                    ratingMap.put(rating, currentCount + 1.0);
                }
            }

            totalPopularity = ratingCount;
        }

        return makeDataSetFromMap(ratingMap, totalPopularity);

    }

    private CategoryDataset makeDataSetFromMap(Map<String, Double> map, double totalPopularity) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String> values = new ArrayList<String>(map.keySet());
        MapComparator comparator = new MapComparator(map);
        Collections.sort(values, comparator);

        double otherPopularity = 0;

        for (int k = 0; k < values.size(); ++k) {
            String value = values.get(k);
            if (value != null && !value.equals("None")) {
                if (k <= 10) {
                    double characterPopularity = map.get(value);
                    dataset.addValue(characterPopularity * 100 / totalPopularity, value, "");
                } else {
                    otherPopularity += map.get(value) / totalPopularity;
                }
            }
        }

        if (otherPopularity != 0) {
            dataset.addValue(otherPopularity * 100, "Other", "");
        }

        return dataset;
    }

    private String normalizePairing(String[] pairings) {
        String pairingsString;
        if (pairings == null || pairings[0] == null) {
            return null;
        }
        if (pairings[0].compareTo(pairings[1]) < 0) {
            pairingsString = "[" + pairings[0] + ", " + pairings[1] + "]";
        } else {
            pairingsString = "[" + pairings[1] + ", " + pairings[0] + "]";
        }
        return pairingsString;
    }

    private class MapComparator implements Comparator<String> {

        private Map<String, Double> map;

        public MapComparator(Map<String, Double> map) {
            this.map = map;
        }

        //So technically this is backwards, but it sorts in the order I want, so we're good.
        @Override
        public int compare(String o1, String o2) {
            return (int) (map.get(o2) - map.get(o1));
        }

    }
}
