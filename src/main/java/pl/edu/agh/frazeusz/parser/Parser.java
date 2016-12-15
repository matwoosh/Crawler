package pl.edu.agh.frazeusz.parser;

import crawler.Crawler;
import crawler.UrlReceiver;

import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private Queue<String> contents;
    private UrlReceiver urlReceiver;

    public Parser(Crawler crawler) {
        contents = new LinkedList<>();
        urlReceiver = new UrlReceiver(crawler);
    }

    private void addUrl(String url) {
        urlReceiver.addUrl(url);
    }

    // This sends parsed Urls
    private void sendUrls() {
        // e.g.
        addUrl("url_1");
    }

    private void update_info() {
        System.out.println("P << Przyszedl nowy Content");
        for (String element : contents) {
            System.out.print("  " + element + " ");
        }
        System.out.println();
    }

    void addContent(String content) {
        contents.add(content);
        update_info();
        sendUrls();            // Send callback
    }
}