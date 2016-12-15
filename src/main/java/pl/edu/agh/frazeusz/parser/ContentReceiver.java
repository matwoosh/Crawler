package pl.edu.agh.frazeusz.parser;

/**
 * Created by Wojtek on 2016-12-14.
 */
public class ContentReceiver {
    private Parser parser;

    public ContentReceiver(Parser parser) {
        this.parser = parser;
    }

    public void addContent(String content) {
        parser.addContent(content);
    }
}
