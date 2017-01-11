package pl.edu.agh.frazeusz.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Url<T> {
    private Url<T> parent = null;
    private List<Url<T>> children = new ArrayList<>();
    String absoluteUrl;

    public Url(String parent) {
        // TODO
    }

    public Url(Url<T> parent) {
        this.parent = parent;
    }

    public void setParent(Url<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public List<Url<T>> getChildren() {
        return children;
    }

    public void addChild(Url<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChildren(List<Url> children) {
        // TODO
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        if (this.children.size() == 0)
            return true;
        else
            return false;
    }

    public void removeParent() {
        this.parent = null;
    }
}
