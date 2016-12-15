package pl.edu.agh.frazeusz.crawler;

import parser.ContentReceiver;
import parser.Parser;
import pl.edu.agh.frazeusz.utilities.Node;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    private Queue<String> urlsToProcess;
    private Set<Node<String>> allUrls;

    private int cores = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor = Executors.newFixedThreadPool(cores);
    private boolean isCrawling;

    private ContentReceiver contentReceiver;

    public Crawler() {
        urlsToProcess = new LinkedList<>();
        allUrls = new HashSet<>();

        Parser parser = new Parser(this);
        contentReceiver = new ContentReceiver(parser);
    }

    public void start(List<String> urlsFromUser) {
        if (!isCrawling) {
            urlsToProcess.addAll(urlsFromUser);
            for (String url : urlsFromUser) {
                allUrls.add(new Node<>(url));
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
        contentReceiver.addContent("content_1");
    }

    private void update_info() {
        System.out.println("C << przyszedl nowy URL");
        for (String element : urlsToProcess) {
            System.out.print("  " + element + " ");
        }
        System.out.println();
    }

    // This is delegated in UrlReceiver
    void addUrl(String url) {
        urlsToProcess.add(url);
        update_info();
        // sendContent();      // Send callback
    }

}