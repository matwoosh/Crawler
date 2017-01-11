package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.parser.Parser;
import pl.edu.agh.frazeusz.utilities.Url;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Downloader implements Runnable {
    private Parser parser;

    Downloader(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void run() {
        System.out.println("Everyday I'm Crawling...");
        // TODO
        // after some execution - sendStats();
    }

    public Url<String> fetchUrl(String url) {
        // TODO
        return null;
    }
}
