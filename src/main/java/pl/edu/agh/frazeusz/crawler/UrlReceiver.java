package pl.edu.agh.frazeusz.crawler;

import java.util.List;

/**
 * Created by Wojtek on 2016-12-14.
 */
public class UrlReceiver {
    private Crawler crawler;

    public UrlReceiver(Crawler crawler) {
        this.crawler = crawler;
    }

    public void addUrlsToCrawl(String baseUrl, List<String> childrenUrls) {
        crawler.addUrlsToCrawl(baseUrl, childrenUrls);
    }
}
