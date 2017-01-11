package pl.edu.agh.frazeusz.parser;

import pl.edu.agh.frazeusz.crawler.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {
    private Queue<String> contents;

    public Parser(Crawler crawler) {
        contents = new LinkedList<>();
    }


    // This sends parsed Urls
    private void sendUrls() {
        // e.g.
        List<String> listOfChildren = new ArrayList<>();
        listOfChildren.add("url_1_child_1");
        listOfChildren.add("url_1_child_2");
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