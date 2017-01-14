package pl.edu.agh.frazeusz.crawler;

import pl.edu.agh.frazeusz.parser.Parser;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Downloader implements Runnable {
    private Crawler crawler;
    private Parser parser;
    private String content;
    private String httpHeader;
    private String url;

    Downloader(Crawler crawler, Parser parser, String url) {
        this.crawler = crawler;
        this.parser = parser;
        this.url = url;
    }

    @Override
    public void run() {
        // TODO

        Url<String> urlNode = fetchUrl(url);

        try {
            if (urlNode != null) {
                parser.parseContent(httpHeader, content, urlNode.getAbsoluteUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" >> WARNING: Parser couldn't parse this url: " + urlNode.getAbsoluteUrl());
        }

        // after some execution - sendStats();
    }

    private Url<String> fetchUrl(String url) {
        // TODO - main ALGO (JSOUP etc...)
        int pageSizeInBytes = 123;
        int processedPages = 1;

        System.out.println("Crawling: " + url);

        this.httpHeader = "Some header...";
        this.content = "Some content...";

        crawler.addPageSizeInBytes(pageSizeInBytes);
        crawler.addProcessedPages(processedPages);

        return null;
    }
}
