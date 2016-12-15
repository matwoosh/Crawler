package pl.edu.agh.frazeusz.crawler;

import monitor.StatsReceiver;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Downloader implements Runnable {
    private StatsReceiver statsReceiver;

    Downloader() {
        statsReceiver = new StatsReceiver();
    }

    @Override
    public void run() {
        System.out.println("Everyday I'm Crawling...");

        // after some execution
        sendStats();
    }

    private void sendStats() {
        // e.g.
        statsReceiver.updateDownloadedBytes("", 123);
        statsReceiver.addRejectedUrl("");
        statsReceiver.addUrlWithNoContent("");
    }

}
