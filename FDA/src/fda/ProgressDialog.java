package fda;

import javax.swing.*;

/**
 * Used to show progress while searching
 * Author: Jonathan Means
 */
public class ProgressDialog extends JDialog {

    //progress bar should show a percentage, takes values from 0 to 100
    private JProgressBar progressBar;

    public ProgressDialog() {
        setTitle("Searching...");
        add(new JLabel("Searching..."));
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
        add(progressBar);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setValue(int value) {
        //progressBar.setValue(value);
    }

    public int getValue() {
        return progressBar.getValue();
    }
}
