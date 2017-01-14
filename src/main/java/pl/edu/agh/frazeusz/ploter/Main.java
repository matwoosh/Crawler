package pl.edu.agh.frazeusz.ploter;

import pl.edu.agh.frazeusz.crawler.Crawler;
import pl.edu.agh.frazeusz.gui.CrawlerGui;
import pl.edu.agh.frazeusz.monitor.Monitor;
import pl.edu.agh.frazeusz.parser.Parser;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        final Parser parser = new Parser();
        final Monitor monitor = new Monitor();
        final Crawler crawler = new Crawler(parser, monitor);

        final JFrame f = new JFrame("Frazeusz");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Container contentPane = f.getContentPane();
                contentPane.setLayout(new BorderLayout());

                // Crawler panel
                CrawlerGui crawlerGui = new CrawlerGui(crawler);
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
}