package pl.edu.agh.frazeusz.parser;

import pl.edu.agh.frazeusz.crawler.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {
    private Queue<String> contents;
    private UrlReceiver urlReceiverImpl;

    public Parser(Crawler crawler) {
        contents = new LinkedList<>();
        urlReceiverImpl = new UrlReceiverImpl(crawler);
    }

    private void addUrlsToCrawl(String baseUrl, List<String> childrenUrls) {
        urlReceiverImpl.addUrlsToCrawl(baseUrl, childrenUrls);
    }

    // This sends parsed Urls
    private void sendUrls() {
        // e.g.
        List<String> listOfChildren = new ArrayList<>();
        listOfChildren.add("url_1_child_1");
        listOfChildren.add("url_1_child_2");
        addUrlsToCrawl("url_1", listOfChildren);
    }

    private void update_info() {
        System.out.println("P << Przyszedl nowy Content");
        for (String element : contents) {
            System.out.print("  " + element + " ");
        }
        System.out.println();
    }

    void addContentToParse(String baseUrl, String content, boolean extractLinks) {
        contents.add(baseUrl);
        update_info();
        sendUrls();            // Send callback
    }
}