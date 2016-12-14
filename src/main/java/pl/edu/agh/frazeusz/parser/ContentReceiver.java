package pl.edu.agh.frazeusz.parser;

/**
 * Created by Mateusz on 14/12/2016.
 */
public interface ContentReceiver {

    void addContentToParse(String baseURL, String content, boolean extractLinks);

}
