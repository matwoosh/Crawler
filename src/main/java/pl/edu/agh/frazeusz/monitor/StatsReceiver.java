package pl.edu.agh.frazeusz.monitor;

public interface StatsReceiver {

    void updateDownloadedBytes(String url, long bytes);

    void addUrlWithNoContent(String url);

    void addRejectedUrl(String url);

}
