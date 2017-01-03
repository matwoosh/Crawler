package pl.edu.agh.frazeusz;

import pl.edu.agh.frazeusz.crawler.Crawler;
import pl.edu.agh.frazeusz.gui.CCPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();

        JFrame f = new JFrame("Crawler");
        new CCPanel(f);

        f.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
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