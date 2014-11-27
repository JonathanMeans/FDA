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
    public enum Preference {WRITER, READER};
    public enum ChartedAttribute {CHARACTER, PAIRING, GENRE, WORDS, RATING};

    //Creating charts takes time, so we're going to have a separate field for each.
    //Then, if the chart already exists, we can just return it.
    private ChartPanel displayChart;
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
        return createPanel(Preference.READER, ChartedAttribute.CHARACTER);
    }

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
            }
        }
        return null;
    }

    private CategoryDataset createCharacterDataSet(Preference preference) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> characterMap = new HashMap<String, Double>();
        double totalPopularity = 0;

        //Not a for-each loop, because I want to track where it breaks off
        int i;
        for (i = 0; i < fics.length; ++i) {
            if (fics[i] == null) { break; }
            double popularityIncrement = fics[i].getPopularity();
            totalPopularity += popularityIncrement;

            for (String character : fics[i].getCharacters()) {
                if (characterMap.get(character) == null) {
                    characterMap.put(character, popularityIncrement);
                } else {
                    double currentPopularity = characterMap.get(character);
                    characterMap.put(character, currentPopularity + popularityIncrement);
                }
            }
        }

        //Finish iterating through the fics
        if (preference == Preference.WRITER) {
            for (int j = i+1; j < fics.length; ++j) {
                //this is duplicate code, but I'm not sure it's worth breaking into its own method....
                double popularityIncrement = fics[j].getPopularity();
                totalPopularity += popularityIncrement;

                for (String character : fics[j].getCharacters()) {
                    if (characterMap.get(character) == null) {
                        characterMap.put(character, popularityIncrement);
                    } else {
                        double currentPopularity = characterMap.get(character);
                        characterMap.put(character, currentPopularity + popularityIncrement);
                    }
                }
            }
        }

        List<String> characters = new ArrayList<String>(characterMap.keySet());
        MapComparator comparator = new MapComparator(characterMap);
        Collections.sort(characters, comparator);

        double otherPopularity = 0;

        for (int k = 0; k < characters.size(); ++k) {
            String character = characters.get(k);
            if (character != null) {
                if (k <= 10) {
                    double characterPopularity = characterMap.get(character);
                    dataset.addValue(characterPopularity * 100/ totalPopularity, character, "");
                } else {
                    otherPopularity += characterMap.get(character) / totalPopularity;
                }
            }
        }

        dataset.addValue(otherPopularity * 100, "Other", "");

        return dataset;

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
