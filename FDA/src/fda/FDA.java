/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fda;

import org.jfree.chart.demo.BarChartDemo1;
import org.jsoup.Jsoup;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FDA extends javax.swing.JFrame {

    /** Creates new form FDA */
    public FDA() {
        initComponents();
    }

/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        displayficPanel = new javax.swing.JPanel();
        searchlinkLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        timerangeComboBox = new javax.swing.JComboBox();
        filterLabel = new javax.swing.JLabel();
        downloadPanel = new javax.swing.JPanel();
        chapterLabel = new javax.swing.JLabel();
        chapterTextField1 = new javax.swing.JTextField();
        toLabel = new javax.swing.JLabel();
        chapterTextField2 = new javax.swing.JTextField();
        downloadlinkLabel = new javax.swing.JLabel();
        downloadTextField = new javax.swing.JTextField();
        DownloadButton = new javax.swing.JButton();
        desktopRadioButton = new javax.swing.JRadioButton();
        downloadLocationLabel = new javax.swing.JLabel();
        CdriveRadioButton = new javax.swing.JRadioButton();
        sortbetaPanel = new javax.swing.JPanel();
        betalinkLabel = new javax.swing.JLabel();
        sortBetaTextField = new javax.swing.JTextField();
        sortBetasearchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fantastical Data Assistant");

        displayficPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Display the top 10 fics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        displayficPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        searchlinkLabel.setText("Fic URL:");

        searchTextField.setText("https://www.fanfiction.net/book/Harry-Potter/");
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchButton.setText("Search");
        searchButton.setToolTipText("Search top 10 fics");
        searchButton.setMaximumSize(new java.awt.Dimension(79, 23));
        searchButton.setMinimumSize(new java.awt.Dimension(79, 23));
        searchButton.setPreferredSize(new java.awt.Dimension(79, 23));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        timerangeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Time Range:All", "Updated within 1 week", "Updated within 1 month", "Updated within 6 month", "Updated within 1 year" }));
        timerangeComboBox.setToolTipText("Time Range Options");
        timerangeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerangeComboBoxActionPerformed(evt);
            }
        });

        filterLabel.setText("Filter:");

        org.jdesktop.layout.GroupLayout displayficPanelLayout = new org.jdesktop.layout.GroupLayout(displayficPanel);
        displayficPanel.setLayout(displayficPanelLayout);
        displayficPanelLayout.setHorizontalGroup(
                displayficPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(displayficPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(displayficPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(displayficPanelLayout.createSequentialGroup()
                                                .add(searchlinkLabel)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(searchTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(displayficPanelLayout.createSequentialGroup()
                                                .add(filterLabel)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(timerangeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(searchButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        displayficPanelLayout.setVerticalGroup(
                displayficPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(displayficPanelLayout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(displayficPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(searchlinkLabel)
                                        .add(searchButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(searchTextField))
                                .add(9, 9, 9)
                                .add(displayficPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(filterLabel)
                                        .add(timerangeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(7, 7, 7))
        );

        searchlinkLabel.getAccessibleContext().setAccessibleName("searchlink");
        searchTextField.getAccessibleContext().setAccessibleName("searchTextfiled");

        downloadPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Download", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        chapterLabel.setText("Chapters:");
        chapterLabel.setToolTipText("");

        chapterTextField1.setEditable(false);
        chapterTextField1.setText("1");

        toLabel.setText("To:");

        chapterTextField2.setText("1");

        downloadlinkLabel.setText("Fic URL:");

        downloadTextField.setText("https://www.fanfiction.net/s/9669819/1/The-Two-Year-Emperor ");
        downloadTextField.setPreferredSize(new java.awt.Dimension(232, 20));
        downloadTextField.setSelectionEnd(45);
        downloadTextField.setSelectionStart(45);
        downloadTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadTextFieldActionPerformed(evt);
            }
        });

        DownloadButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        DownloadButton.setText("Download");
        DownloadButton.setToolTipText("Download fic");
        DownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownloadButton1ActionPerformed(evt);
            }
        });

        desktopRadioButton.setText("Desktop");
        desktopRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        desktopRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));

        downloadLocationLabel.setText("Location:");
        downloadLocationLabel.setToolTipText("");

        CdriveRadioButton.setText("Cdrive");
        CdriveRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        CdriveRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        CdriveRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CdriveRadioButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout downloadPanelLayout = new org.jdesktop.layout.GroupLayout(downloadPanel);
        downloadPanel.setLayout(downloadPanelLayout);
        downloadPanelLayout.setHorizontalGroup(
                downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(downloadPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(downloadPanelLayout.createSequentialGroup()
                                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                        .add(chapterLabel)
                                                        .add(downloadLocationLabel))
                                                .add(46, 46, 46)
                                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                                        .add(chapterTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                                        .add(desktopRadioButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .add(12, 12, 12)
                                                .add(toLabel)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                                        .add(CdriveRadioButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .add(chapterTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                        .add(downloadPanelLayout.createSequentialGroup()
                                                .add(downloadlinkLabel)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(downloadTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 371, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(18, 18, Short.MAX_VALUE)
                                .add(DownloadButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        downloadPanelLayout.setVerticalGroup(
                downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(downloadPanelLayout.createSequentialGroup()
                                .add(16, 16, 16)
                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(downloadlinkLabel)
                                        .add(downloadTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(DownloadButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(17, 17, 17)
                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(chapterLabel)
                                        .add(chapterTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(chapterTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(toLabel))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 17, Short.MAX_VALUE)
                                .add(downloadPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(desktopRadioButton)
                                        .add(downloadLocationLabel)
                                        .add(CdriveRadioButton)))
        );

        chapterLabel.getAccessibleContext().setAccessibleName("chapters");
        chapterTextField1.getAccessibleContext().setAccessibleName("");
        toLabel.getAccessibleContext().setAccessibleName("To");
        downloadlinkLabel.getAccessibleContext().setAccessibleName("downloadlink");
        downloadTextField.getAccessibleContext().setAccessibleName("download");
        downloadTextField.getAccessibleContext().setAccessibleDescription("\\");
        downloadLocationLabel.getAccessibleContext().setAccessibleName("downloadLocation");

        sortbetaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "listings of beta readers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        sortbetaPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        betalinkLabel.setText("Fic URL:");

        sortBetaTextField.setText("https://www.fanfiction.net/betareaders/anime/Anima/");
        sortBetaTextField.setSelectionEnd(45);
        sortBetaTextField.setSelectionStart(45);
        sortBetaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortBetaTextFieldActionPerformed(evt);
            }
        });

        sortBetasearchButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        sortBetasearchButton.setText("Sort");
        sortBetasearchButton.setToolTipText("Sort beata reader");
        sortBetasearchButton.setFocusCycleRoot(true);
        sortBetasearchButton.setMaximumSize(new java.awt.Dimension(79, 23));
        sortBetasearchButton.setMinimumSize(new java.awt.Dimension(79, 23));
        sortBetasearchButton.setPreferredSize(new java.awt.Dimension(79, 23));
        sortBetasearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortBetasearchButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sortbetaPanelLayout = new org.jdesktop.layout.GroupLayout(sortbetaPanel);
        sortbetaPanel.setLayout(sortbetaPanelLayout);
        sortbetaPanelLayout.setHorizontalGroup(
                sortbetaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(sortbetaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(betalinkLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(sortBetaTextField)
                                .add(44, 44, 44)
                                .add(sortBetasearchButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        sortbetaPanelLayout.setVerticalGroup(
                sortbetaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(sortbetaPanelLayout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(sortbetaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(sortBetaTextField)
                                        .add(betalinkLabel)
                                        .add(sortBetasearchButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        betalinkLabel.getAccessibleContext().setAccessibleName("betalink");
        sortBetaTextField.getAccessibleContext().setAccessibleName("sortBeta");
        sortBetasearchButton.getAccessibleContext().setAccessibleDescription("Sort beta reader");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(downloadPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, displayficPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(sortbetaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(displayficPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(downloadPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(28, 28, 28)
                                .add(sortbetaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displayficPanel.getAccessibleContext().setAccessibleName("display10");
        sortbetaPanel.getAccessibleContext().setAccessibleName("sortBeta");

        pack();
    }// </editor-fold>
    public enum UrlType{FANDOM, FIC, BETA, UNKNOWN}
    public static final String BASE_URL = "https://www.fanfiction.net";
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO Search button:

        // validateURL validURl = new validateURL();
        if (UrlType.FANDOM== checkUrl(searchTextField.getText()))
        {
            //do search
            System.out.println("and here do search");
        }
        else
        {
            //show error message
            String message = "\"The search fic link is not recognized,\"\n"
                    + "please try again.";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void DownloadButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // // TODO download button:
        // validateURL validURl = new validateURL();
        if (UrlType.FIC == checkUrl(downloadTextField.getText()))
        {
            //do search
            System.out.println("and here do download");
        }
        else
        {
            //show error message
            String message = "\"The download fic link is not recognized,\"\n"
                    + "please try again.";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortBetasearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // // TODO sort beta reader button:
        // validateURL validURl = new validateURL();
        if (UrlType.BETA == checkUrl(sortBetaTextField.getText()))
        {
            //do sort beta
            System.out.println("and here do sorting");
        }
        else
        {
            //show error message
            String message = "\"The beta link is not recognized,\"\n"
                    + "please try again.";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CdriveRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void downloadTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void sortBetaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void timerangeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO filter display top 10 fics:
        // get the selected item:
        String selectedBook = (String) timerangeComboBox.getSelectedItem();
        System.out.println("Filter: " + selectedBook);
    }
    public UrlType checkUrl(String url) {
        String ficString = BASE_URL + "/s/\\d+/\\d+/.+";
        String betaString = BASE_URL + "/betareaders/.+";
        String fandomString = BASE_URL + "/.+";
        //Test whether URL matches the fic pattern
        Pattern storyPattern = Pattern.compile(ficString);
        Matcher storyMatcher = storyPattern.matcher(url);
        if (storyMatcher.find()) {
            return UrlType.FIC;
        }
        //Test whether URL matches the beta listing pattern
        Pattern betaPattern = Pattern.compile(betaString);
        Matcher betaMatcher = betaPattern.matcher(url);
        if (betaMatcher.find()) { return UrlType.BETA; }
        //Test whether URL matches the fandom pattern
        Pattern fandomPattern = Pattern.compile(fandomString);
        Matcher fandomMatcher = fandomPattern.matcher(url);
        if (fandomMatcher.find()) { return UrlType.FANDOM; }
        return UrlType.UNKNOWN;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JRadioButton CdriveRadioButton;
    private javax.swing.JButton DownloadButton;
    private javax.swing.JLabel betalinkLabel;
    private javax.swing.JLabel chapterLabel;
    private javax.swing.JTextField chapterTextField1;
    private javax.swing.JTextField chapterTextField2;
    private javax.swing.JRadioButton desktopRadioButton;
    private javax.swing.JPanel displayficPanel;
    private javax.swing.JLabel downloadLocationLabel;
    private javax.swing.JPanel downloadPanel;
    private javax.swing.JTextField downloadTextField;
    private javax.swing.JLabel downloadlinkLabel;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel searchlinkLabel;
    private javax.swing.JTextField sortBetaTextField;
    private javax.swing.JButton sortBetasearchButton;
    private javax.swing.JPanel sortbetaPanel;
    private javax.swing.JComboBox timerangeComboBox;
    private javax.swing.JLabel toLabel;
    // End of variables declaration                   

}
