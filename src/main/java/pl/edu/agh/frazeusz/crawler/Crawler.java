package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.gui.CrawlerGui;
import pl.edu.agh.frazeusz.monitor.Monitor;
import pl.edu.agh.frazeusz.parser.Parser;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    private CrawlerGui crawlerGui;
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
        this.crawlerGui = new CrawlerGui();
        this.monitor = monitor;
        this.parser = parser;

        urlsToProcess = new LinkedList<>();
        allUrls = new HashSet<>();

        this.processedPages = 0;
        this.pageSizeInBytes = 0;
    }

    public CrawlerGui getPanel() {
        return crawlerGui;
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

        // Crawling started
        isCrawling = true;

        // Concurrent tasks
        // or Threadpool or etc...
        for (int i = 0; i < nrOfThreads; i++) {
            // TODO - simple example
            if (urlsToProcess.peek() != null) {
                Downloader downloader = new Downloader(this, parser, urlsToProcess.poll());
                executor.execute(downloader);
            }
        }

        // Crawling finished
        isCrawling = false;
    }

    private void sendStatsToMonitor() {
        monitor.addProcessedPages(processedPages, pageSizeInBytes);
        monitor.setPagesQueueSize(allUrls.size());
        // TODO
    }
}