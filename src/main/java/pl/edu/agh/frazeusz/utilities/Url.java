package pl.edu.agh.frazeusz.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mateusz on 14/12/2016.
 */
public class Url<T> {

    private List<Url<T>> children = new ArrayList<Url<T>>();
    private Url<T> parent = null;
    private T data = null;

    public Url(T data) {
        this.data = data;
    }

    public Url(T data, Url<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<Url<T>> getChildren() {
        return children;
    }

    public void setParent(Url<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(T data) {
        Url<T> child = new Url<T>(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(Url<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url<?> url = (Url<?>) o;
        return Objects.equals(data, url.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
