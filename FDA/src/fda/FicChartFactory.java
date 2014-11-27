package fda;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }

        for (String character : characterMap.keySet()) {
            if (character != null) {
                double characterPopularity = characterMap.get(character);
                characterMap.put(character, characterPopularity / totalPopularity);
                dataset.addValue(characterPopularity / totalPopularity, character, character);
            }
        }

        return dataset;

    }

}
