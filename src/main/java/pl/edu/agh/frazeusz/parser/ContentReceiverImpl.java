package pl.edu.agh.frazeusz.parser;

/**
 * Created by Wojtek on 2016-12-14.
 */
public class ContentReceiverImpl implements ContentReceiver {
    private Parser parser;

    public ContentReceiverImpl(Parser parser) {
        this.parser = parser;
    }

    public void addContentToParse(String baseURL, String content, boolean extractLinks) {
        parser.addContentToParse(baseURL, content, extractLinks);
    }
}
