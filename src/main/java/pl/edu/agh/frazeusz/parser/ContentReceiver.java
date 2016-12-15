package pl.edu.agh.frazeusz.parser;

/**
 * Created by Wojtek on 2016-12-15.
 */
public interface ContentReceiver {
    void addContentToParse(String baseURL, String content, boolean extractLinks);

}
