package pl.edu.agh.frazeusz;

import pl.edu.agh.frazeusz.crawler.Crawler;

import java.util.ArrayList;
import java.util.List;

public class Main {
     public static void main(String[] args) {
        Crawler crawler = new Crawler();
        List<String> input_urls = new ArrayList<>();

        // e.g.
        input_urls.add("input_1");
        input_urls.add("input_2");

        crawler.start(input_urls);

        crawler.stop();
    }
}