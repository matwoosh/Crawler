package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.parser.*;
import pl.edu.agh.frazeusz.utilities.Url;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    private Queue<String> urlsToProcess;
    private Set<Url<String>> allUrls;

    private int cores = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor = Executors.newFixedThreadPool(cores);
    private boolean isCrawling;

    private ContentReceiver contentReceiverImpl;

    public Crawler() {
        urlsToProcess = new LinkedList<>();
        allUrls = new HashSet<>();

        Parser parser = new Parser(this);
        contentReceiverImpl = new ContentReceiverImpl(parser);
    }

    public void start(List<String> urlsFromUser) {
        if (!isCrawling) {
            urlsToProcess.addAll(urlsFromUser);
            for (String url : urlsFromUser) {
                allUrls.add(new Url<>(url));
            }

            initializeDownloaders();
        }
    }

    public void stop() {
        stop_threads();
        this.isCrawling = false;
    }

    private void stop_threads() {
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private void initializeDownloaders() {
        // Concurrent tasks
        for (int i = 0; i < cores; i++) {
            Downloader downloader = new Downloader();
            executor.execute(downloader);
        }

        // or Threadpool or etc...
        sendContent();
    }

    // This sends downloaded Content
    private void sendContent() {
        // e.g.
        contentReceiverImpl.addContentToParse("baseUrl_1", "content_1", true);
    }

    private void update_info() {
        System.out.println("C << przyszedl nowy URL");
        for (String element : urlsToProcess) {
            System.out.print("  " + element + " ");
        }
        System.out.println();
    }

    // This is delegated in UrlReceiverImpl
    void addUrlsToCrawl(String baseUrl, List<String> childrenUrls) {
        urlsToProcess.add(baseUrl);
        // Nodes ...

        update_info();
        // sendContent();      // Send callback
    }

}