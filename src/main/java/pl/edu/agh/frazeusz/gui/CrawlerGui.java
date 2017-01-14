package pl.edu.agh.frazeusz.gui;

import pl.edu.agh.frazeusz.crawler.CrawlerConf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Wojtek on 2017-01-03.
 */

public class CrawlerGui extends JPanel {
    // Defaults
    private int nrOfDefaultThreads = Runtime.getRuntime().availableProcessors();
    private int nrOfDefaultDepth = 2;
    // Parameters to be passed to Crawler
    private int nrOfChosenThreads;
    private int nrOfChosenDepth;
    private ArrayList<String> urlsToCrawl;
    // GUI components
    private JPanel contentPanel;
    private JLabel labelLinks;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JLabel labelThreads1;
    private JSpinner spinnerThreads;
    private JLabel labelDepth1;
    private JSpinner spinnerDepth;
    private JLabel labelThreads2;
    private JLabel labelDepth2;

    public CrawlerGui() {
        nrOfChosenThreads = nrOfDefaultThreads;
        nrOfChosenDepth = nrOfDefaultDepth;
        urlsToCrawl = new ArrayList<>();
        initComponents();
    }

    public CrawlerConf getConf() {
        // Get URLs from textarea to List which will be further passed to Crawler.
        for (String line : textArea.getText().split("\\n")) {
            if (!line.isEmpty()) {
                urlsToCrawl.add(line);
                System.out.println("> " + line);
            }
        }

        return new CrawlerConf(urlsToCrawl, nrOfChosenThreads, nrOfChosenDepth);
    }

    public void resetFields() {
        textArea.setText("");
        spinnerThreads.setValue(nrOfDefaultThreads);
        spinnerDepth.setValue(nrOfDefaultDepth);
        System.out.println("> textArea: " + textArea.getText() + ", Threads: " + nrOfChosenThreads + ", Depth: " + nrOfChosenDepth);
    }

    private void initComponents() {
        contentPanel = new JPanel();
        labelLinks = new JLabel();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        labelThreads1 = new JLabel();
        spinnerThreads = new JSpinner();
        labelDepth1 = new JLabel();
        spinnerDepth = new JSpinner();
        labelThreads2 = new JLabel();
        labelDepth2 = new JLabel();

        //======== Main JPanel (this) ========
        {
            this.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                initContentPanel();
                // ===== LAYOUT =====
                initLayout();
            }
            this.add(contentPanel, BorderLayout.NORTH);
        }
    }

    private void initContentPanel() {
        //---- labelLinks ----
        labelLinks.setText("Input links:");

        //---- scrollPane ----
        scrollPane.setViewportView(textArea);

        //---- labelThreads1 ----
        labelThreads1.setText("Threads:");

        //---- spinnerThreads ----
        SpinnerModel valueOfThreads = new SpinnerNumberModel(nrOfDefaultThreads, 1, 10000, 1);
        spinnerThreads.setModel(valueOfThreads);

        //---- labelThreads2 ----
        labelThreads2.setText("1 - 10000 (default: " + nrOfDefaultThreads + ")");

        spinnerThreads.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                nrOfChosenThreads = (int) ((JSpinner) e.getSource()).getValue();
                System.out.println("> Threads: " + (int) ((JSpinner) e.getSource()).getValue());
            }
        });

        //---- labelDepth1 ----
        labelDepth1.setText("Depth:");

        //---- spinnerDepth ----
        SpinnerModel valueOfDepth = new SpinnerNumberModel(nrOfDefaultDepth, 1, 10, 1);
        spinnerDepth.setModel(valueOfDepth);

        //---- labelDepth2 ----
        labelDepth2.setText("1 - 10 (default: " + nrOfDefaultDepth + ")");

        spinnerDepth.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                nrOfChosenDepth = (int) ((JSpinner) e.getSource()).getValue();
                System.out.println("> Depth: " + (int) ((JSpinner) e.getSource()).getValue());
            }
        });
    }

    private void initLayout() {
        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup()
                        .addComponent(scrollPane)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(labelLinks)
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addGroup(contentPanelLayout.createParallelGroup()
                                                        .addComponent(labelThreads1)
                                                        .addComponent(labelDepth1))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(contentPanelLayout.createParallelGroup()
                                                        .addComponent(spinnerThreads, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                                        .addComponent(spinnerDepth))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(labelThreads2)
                                        .addComponent(labelDepth2))
                                .addGap(158, 158, 158))
        );
        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addComponent(labelLinks)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelThreads1)
                                        .addComponent(spinnerThreads)
                                        .addComponent(labelThreads2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelDepth1)
                                        .addComponent(spinnerDepth)
                                        .addComponent(labelDepth2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addContainerGap())
        );
    }

}
