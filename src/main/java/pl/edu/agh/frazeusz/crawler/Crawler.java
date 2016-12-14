package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.utilities.Node;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Crawler implements URLReceiver{

    private final ArrayDeque<String> urlsToProcess;
    private final Set<Node<String>> baseUrls;
    private boolean isCrawling;

    public Crawler() {
        this.urlsToProcess = new ArrayDeque<>();
        this.baseUrls = new HashSet<>();
    }

    public void start(List<String> urlsFromUser) {
        if(!isCrawling){
            this.isCrawling = true;
            urlsToProcess.addAll(urlsFromUser);
            for (String url: urlsFromUser
                 ) {
                baseUrls.add(new Node<String>(url));
            }
            initializeDownloaders();
        }
    }

    public void stop() {
        clear();
        this.isCrawling = false;
    }

    private void initializeDownloaders(){
        Downloader downloader1 = new Downloader();
        Downloader downloader2 = new Downloader();
        downloader1.run();
        downloader2.run();
    }

    private void clear() {

    }

    public void addUrlsToCrawl(String baseUrl, List<String> childrenUrls) {

    }

}
