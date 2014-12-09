/*
 * GUI for displaying the results of a fandom search.
 * Authors: Yuwen Huang, Jonathan Means
 */
package fda.Graphics;


import fda.Containers.Fanfic;
import fda.Scrapers.FandomScraper;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class fanficJFrame extends JFrame {

    /**
     * Creates new form fanficJFrame
     */
    public fanficJFrame(String url, int numDays) throws IOException {
        initComponents(url, numDays);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(String url, int numDays) throws IOException {

        preference = FicChartFactory.Preference.READER;
        chartedAttribute = FicChartFactory.ChartedAttribute.CHARACTER;

        Fanfic[] fics = FandomScraper.extractFics(url, numDays);
        final FicChartFactory chartFactory = new FicChartFactory(fics);

        JPanel graphPanel = new JPanel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        chartPanel = chartFactory.createPanel(preference, chartedAttribute);
        JRadioButton writerButton = new javax.swing.JRadioButton();
        JRadioButton readerRadioButton = new javax.swing.JRadioButton();
        JRadioButton ratingButton = new javax.swing.JRadioButton();
        JRadioButton genresButton = new javax.swing.JRadioButton();
        JRadioButton wordCntButton = new javax.swing.JRadioButton();
        JRadioButton pairingsButton = new javax.swing.JRadioButton();
        JRadioButton characterButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        graphPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Graph"));

        jScrollPane1.setViewportView(chartPanel);

        writerButton.setText("Writer preferred");
        writerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preference = FicChartFactory.Preference.WRITER;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        readerRadioButton.setText("Reader preferred");
        readerRadioButton.setSelected(true);
        readerRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preference = FicChartFactory.Preference.READER;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        ratingButton.setText("Rating data");
        ratingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chartedAttribute = FicChartFactory.ChartedAttribute.RATING;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        genresButton.setText("Genre data");
        genresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chartedAttribute = FicChartFactory.ChartedAttribute.GENRE;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        wordCntButton.setText("Word Count data");
        wordCntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chartedAttribute = FicChartFactory.ChartedAttribute.WORDS;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        pairingsButton.setText("Pairings data");
        pairingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chartedAttribute = FicChartFactory.ChartedAttribute.PAIRING;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });


        characterButton.setText("Character data");
        characterButton.setSelected(true);
        characterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chartedAttribute = FicChartFactory.ChartedAttribute.CHARACTER;
                chartPanel = chartFactory.createPanel(preference, chartedAttribute);
            }
        });

        ButtonGroup dataGroup = new ButtonGroup();
        dataGroup.add(ratingButton);
        dataGroup.add(genresButton);
        dataGroup.add(wordCntButton);
        dataGroup.add(pairingsButton);
        dataGroup.add(characterButton);

        ButtonGroup preferenceGroup = new ButtonGroup();
        preferenceGroup.add(writerButton);
        preferenceGroup.add(readerRadioButton);

        javax.swing.GroupLayout grathPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(grathPanelLayout);
        grathPanelLayout.setHorizontalGroup(
                grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(grathPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(grathPanelLayout.createSequentialGroup()
                                                .addGroup(grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(grathPanelLayout.createSequentialGroup()
                                                                .addComponent(writerButton)
                                                                .addGap(100, 100, 100)
                                                                .addComponent(readerRadioButton))
                                                        .addGroup(grathPanelLayout.createSequentialGroup()
                                                                .addComponent(ratingButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(genresButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(wordCntButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(pairingsButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(characterButton)))
                                                .addGap(0, 101, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        grathPanelLayout.setVerticalGroup(
                grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(grathPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, 300, 504, Short.MAX_VALUE)
                                .addGap(25, 25, 25)
                                .addGroup(grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ratingButton)
                                        .addComponent(genresButton)
                                        .addComponent(wordCntButton)
                                        .addComponent(pairingsButton)
                                        .addComponent(characterButton))
                                .addGap(23, 23, 23)
                                .addGroup(grathPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(writerButton)
                                        .addComponent(readerRadioButton)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    // Variables declaration - do not modify
    private ChartPanel chartPanel;
    private FicChartFactory.Preference preference;
    private FicChartFactory.ChartedAttribute chartedAttribute;
    // End of variables declaration
}