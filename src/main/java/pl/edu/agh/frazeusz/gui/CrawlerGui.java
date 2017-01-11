package pl.edu.agh.frazeusz.gui;

import pl.edu.agh.frazeusz.crawler.Crawler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Wojtek on 2017-01-03.
 */

public class CrawlerGui {
    private final Crawler crawler;
    // Defaults
    private int nrOfDefaultThreads = Runtime.getRuntime().availableProcessors();
    private int nrOfDefaultDepth = 2;

    // Parameters to be passed to Crawler
    private int nrOfChosenThreads;
    private int nrOfChosenDepth;
    private ArrayList<String> urlsToCrawl;

    // GUI components
    private JFrame f;
    private JPanel dialogPane;
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
    private JPanel buttonBar;
    private JLabel labelStatus;
    private JButton buttonStart;
    private JButton buttonReset;

    // Flags to determine start & stop activity of GUI and Crawler
    private boolean isCrawlerWorking;
    private boolean isInterrupted = false;

    public CrawlerGui(JFrame f, Crawler crawler) {
        this.f = f;
        this.crawler = crawler;
        nrOfChosenThreads = nrOfDefaultThreads;
        nrOfChosenDepth = nrOfDefaultDepth;
        urlsToCrawl = new ArrayList<>();
        initComponents();
    }

    public ArrayList<String> getUrlsToCrawl() {
        return urlsToCrawl;
    }

    public void setValuesOfDepth(int nrOfDefaultDepth, int min, int max, int stepSize) {
        this.nrOfDefaultDepth = nrOfDefaultDepth;
        nrOfChosenDepth = nrOfDefaultDepth;

        SpinnerModel valueOfDepth = new SpinnerNumberModel(nrOfDefaultDepth, min, max, stepSize);
        spinnerDepth.setModel(valueOfDepth);

        //---- labelDepth2 ----
        labelDepth2.setText(min + " - " + max + " (default: " + nrOfDefaultDepth + ")");
    }

    public void setValuesOfThreads(int nrOfDefaultThreads, int min, int max, int stepSize) {
        this.nrOfDefaultThreads = nrOfDefaultThreads;
        nrOfChosenThreads = nrOfDefaultThreads;

        SpinnerModel valueOfThreads = new SpinnerNumberModel(nrOfDefaultThreads, min, max, stepSize);
        spinnerThreads.setModel(valueOfThreads);

        //---- labelThreads2 ----
        labelThreads2.setText(min + " - " + max + " (default: " + nrOfDefaultThreads + ")");
    }

    private void initComponents() {
        dialogPane = new JPanel();
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
        buttonBar = new JPanel();
        labelStatus = new JLabel();
        buttonStart = new JButton();
        buttonReset = new JButton();

        //======== this ========
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                initContentPanel();
                // ===== buttonBar =====
                initButtonBar();
                // ===== LAYOUT =====
                initLayout();
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
        }

        contentPane.add(dialogPane, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(f.getOwner());
    }

    private void initContentPanel() {
        //---- labelLinks ----
        labelLinks.setText("Input links:");

        //---- scrollPane ----
        scrollPane.setViewportView(textArea);

        //---- labelThreads1 ----
        labelThreads1.setText("Threads:");

        //---- spinnerThreads ----
        setValuesOfThreads(nrOfDefaultThreads, 1, 10000, 1);
        spinnerThreads.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                nrOfChosenThreads = (int) ((JSpinner) e.getSource()).getValue();
                System.out.println("> Threads: " + (int) ((JSpinner) e.getSource()).getValue());
            }
        });

        //---- labelDepth1 ----
        labelDepth1.setText("Depth:");

        //---- spinnerDepth ----
        setValuesOfDepth(nrOfDefaultDepth, 1, 10, 2);
        spinnerDepth.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                nrOfChosenDepth = (int) ((JSpinner) e.getSource()).getValue();
                System.out.println("> Depth: " + (int) ((JSpinner) e.getSource()).getValue());
            }
        });
    }

    private void initButtonBar() {
        buttonBar.setBorder(new EmptyBorder(5, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 40};
        ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0};

        //---- labelStatus ----
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatus.setPreferredSize(new Dimension(0, 16));
        labelStatus.setMinimumSize(new Dimension(0, 16));
        labelStatus.setMaximumSize(new Dimension(0, 16));
        labelStatus.setText("");
        labelStatus.setVisible(false);
        buttonBar.add(labelStatus, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- buttonStart ----
        buttonStart.setText("Start");
        buttonStart.addActionListener(new StartButtonListener());

        buttonBar.add(buttonStart, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        //---- buttonReset ----
        buttonReset.setText("Reset");
        buttonBar.add(buttonReset, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        buttonReset.addActionListener(new ResetButtonListener());
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
                        .addComponent(buttonBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(buttonBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }

    private void setComponentsEnabled(boolean enabled) {
        buttonReset.setEnabled(enabled);
        textArea.setEnabled(enabled);
        spinnerThreads.setEnabled(enabled);
        spinnerDepth.setEnabled(enabled);
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
            spinnerThreads.setValue(nrOfDefaultThreads);
            spinnerDepth.setValue(nrOfDefaultDepth);
            labelStatus.setVisible(false);
            System.out.println("> textArea: " + textArea.getText() + ", Threads: " + nrOfChosenThreads + ", Depth: " + nrOfChosenDepth);
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CountDownLatch latch = new CountDownLatch(1);   // Only ONE Crawler can be running at the same time
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            CrawlingWorker crawlingWorker = new CrawlingWorker(latch);

            if (!isCrawlerWorking) {
                setComponentsEnabled(false);
                isCrawlerWorking = true;
                isInterrupted = false;
                labelStatus.setText("Crawling...");
                labelStatus.setForeground(new Color(0, 0, 200));
                labelStatus.setVisible(true);

                executorService.execute(crawlingWorker);
                System.out.println("> Crawling started...");
            } else {
                crawlingWorker.cancel(true);
            }
        }
    }

    private class CrawlingWorker extends SwingWorker<Void, Void> {
        CountDownLatch latch;

        CrawlingWorker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        protected Void doInBackground() throws Exception {
            buttonStart.setText("Stop !");
            System.out.println("> Threads: " + nrOfChosenThreads + ", Depth: " + nrOfChosenDepth);

            // Get URLs from textarea to List which will be further passed to Crawler.
            for (String line : textArea.getText().split("\\n")) {
                if (!line.isEmpty()) {
                    urlsToCrawl.add(line);
                    System.out.println("> " + line);
                }
            }

            crawler.start(urlsToCrawl, nrOfChosenThreads, nrOfChosenDepth);

            while (crawler.isCrawling()) {
                if (isInterrupted) {
                    crawler.stop();
                }
            }

            // Example of terminating
            int countdown = 1;
            while (countdown <= 10) {
                if (isInterrupted) {
                    break;
                }

                System.out.println("> " + countdown);
                ++countdown;

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void done() {
            setComponentsEnabled(true);
            urlsToCrawl.clear();
            buttonStart.setText("Start");

            if (!isCancelled() && !isInterrupted) {
                labelStatus.setText("Done !");
                labelStatus.setForeground(new Color(0, 140, 0));
                System.out.println("> Crawling done !");
            } else {
                isInterrupted = true;
                labelStatus.setText("Interrupted !");
                labelStatus.setForeground(new Color(180, 0, 0));
                System.out.println("> Crawling interrupted !");
            }

            latch.countDown();
            isCrawlerWorking = false;
        }

    }

}
