package pl.edu.agh.frazeusz.ploter;

import pl.edu.agh.frazeusz.crawler.Crawler;
import pl.edu.agh.frazeusz.crawler.CrawlerConf;
import pl.edu.agh.frazeusz.gui.CrawlerGui;
import pl.edu.agh.frazeusz.monitor.Monitor;
import pl.edu.agh.frazeusz.parser.Parser;

import javax.swing.*;
import java.awt.*;

public class Ploter {
    private static final Parser parser = new Parser();
    private static final Monitor monitor = new Monitor();
    private static final Crawler crawler = new Crawler(parser, monitor);
    private static final CrawlerGui crawlerGui = crawler.getPanel();
    private boolean isInterrupted;

    public Ploter() {
        isInterrupted = false;
    }

    public static void main(String[] args) {
        startGUI();
    }

    private static void startGUI() {
        final JFrame f = new JFrame("Frazeusz");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Container contentPane = f.getContentPane();
                contentPane.setLayout(new BorderLayout());

                // Crawler panel
                contentPane.add(crawlerGui, BorderLayout.NORTH);

                // Other panels:
                // e.g.: contentPane.add(otherPanel, BorderLayout.SOUTH);

                f.pack();
                f.setLocationRelativeTo(f.getOwner());
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(440, 250);
        f.getContentPane().setLayout(null);
        f.setVisible(true);
    }

    private void startCrawling() {
        // In new thread
        CrawlerConf crawlerConf = crawler.getPanel().getConf();
        crawler.start(crawlerConf.getUrlsToCrawl(), crawlerConf.getNrOfChosenThreads(), crawlerConf.getNrOfChosenDepth());

        while (crawler.isCrawling()) {
            if (isInterrupted) {
                crawler.stop();
            }
        }
    }
}