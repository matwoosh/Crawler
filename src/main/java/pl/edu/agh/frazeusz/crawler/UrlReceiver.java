package pl.edu.agh.frazeusz.crawler;

import java.util.List;

/**
 * Created by Wojtek on 2016-12-15.
 */
public interface UrlReceiver {
    void addUrlsToCrawl(String baseUrl, List<String> childrenUrls);

}
