package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.monitor.Monitor;
import pl.edu.agh.frazeusz.parser.Parser;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    private Monitor monitor;
    private Parser parser;
    private Queue<String> urlsToProcess;
    private Set<Url<String>> allUrls;
    private int nrOfThreads;
    private int nrOfDepth;

    private ExecutorService executor;
    private boolean isCrawling;
    private int processedPages;
    private long pageSizeInBytes;

    public Crawler(Parser parser, Monitor monitor) {
        this.monitor = monitor;
        this.parser = parser;
        this.processedPages = 0;
        this.pageSizeInBytes = 0;

        urlsToProcess = new LinkedList<>();
        allUrls = new HashSet<>();
    }

    void addProcessedPages(int processedPages) {
        this.processedPages += processedPages;
    }

    void addPageSizeInBytes(long pageSizeInBytes) {
        this.pageSizeInBytes += pageSizeInBytes;
    }

    public void start(ArrayList<String> urlsFromUser, int nrOfThreads, int nrOfDepth) {
        this.urlsToProcess.addAll(urlsFromUser);
        this.nrOfThreads = nrOfThreads;
        this.nrOfDepth = nrOfDepth;
        executor = Executors.newFixedThreadPool(nrOfThreads);

        System.out.println(" >>> Got depth: " + nrOfDepth + " threads: " + nrOfThreads + "\n >>> Got Urls:");
        for (String url : urlsFromUser) {
            System.out.println("    + " + url);
        }

        if (!isCrawling) {
            for (String url : urlsToProcess) {
                allUrls.add(new Url<String>(url));
            }

            initializeDownloaders();
        }
    }

    public void stop() {
        stopThreads();
        this.isCrawling = false;
    }

    public boolean isCrawling() {
        return isCrawling;
    }

    private void stopThreads() {
        executor.shutdown();
        while (!executor.isTerminated()) {
            // TODO
        }
    }

    private void initializeDownloaders() {
        // TODO

        // Concurrent tasks
        for (int i = 0; i < nrOfThreads; i++) {
            // TODO - simple example
            if (urlsToProcess.peek() != null) {
                Downloader downloader = new Downloader(this, parser, urlsToProcess.poll());
                executor.execute(downloader);
            }
        }

        // or Threadpool or etc...
    }

    private void sendStatsToMonitor() {
        monitor.addProcessedPages(processedPages, pageSizeInBytes);
        monitor.setPagesQueueSize(allUrls.size());
        // TODO
    }
}