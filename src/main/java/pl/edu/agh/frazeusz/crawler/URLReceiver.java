package pl.edu.agh.frazeusz.crawler;

import java.util.List;

public interface URLReceiver {

    void start(List<String> URLs);

    void addUrlsToCrawl(String baseUrl, List<String> childrenUrls);

}
