package pl.edu.agh.frazeusz;

import pl.edu.agh.frazeusz.crawler.Crawler;
import pl.edu.agh.frazeusz.gui.CrawlerGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final Crawler crawler = new Crawler();
        final JFrame f = new JFrame("Crawler");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrawlerGui(f, crawler);
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(440, 250);
        f.getContentPane().setLayout(null);
        f.setVisible(true);

//        List<String> input_urls = new ArrayList<>();
//
//        // e.g.
//        input_urls.add("input_1");
//        input_urls.add("input_2");
//
//        crawler.start(input_urls);
//
//        crawler.stop();
    }
}