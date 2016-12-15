package pl.edu.agh.frazeusz.crawler;

/**
 * Created by Wojtek on 2016-12-14.
 */
public class UrlReceiver {
    private Crawler crawler;

    public UrlReceiver(Crawler crawler) {
        this.crawler = crawler;
    }

    public void addUrl(String url) {
        crawler.addUrl(url);
    }
}
