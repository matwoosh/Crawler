package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.parser.Parser;
import pl.edu.agh.frazeusz.utilities.Url;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crawler {
    private Queue<String> urlsToProcess;
    private Set<Url<String>> allUrls;

    private ExecutorService executor;
    private boolean isCrawling;

    private int nrOfThreads;
    private int nrOfDepth;

    public Crawler() {
        urlsToProcess = new LinkedList<>();
        allUrls = new HashSet<>();

        Parser parser = new Parser(this);
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
                //allUrls.add(new Url<>(url)); // TODO
            }

            initializeDownloaders();
        }
    }

    public boolean isCrawling() {
        return isCrawling;
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
        for (int i = 0; i < nrOfThreads; i++) {
            Downloader downloader = new Downloader();
            executor.execute(downloader);
        }

        // or Threadpool or etc...
    }

    private void update_info() {
        System.out.println("C << przyszedl nowy URL");
        for (String element : urlsToProcess) {
            System.out.print("  " + element + " ");
        }
        System.out.println();
    }

}