package main.java.com.ibrdtnapplication;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Observable<T> {
    private PropertyChangeSupport support;
    private String name;

    public Observable(String name) {
        support = new PropertyChangeSupport(this);
        this.name = name;
    }

    public void addObserver(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removeObserver(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void update(T value) {
        support.firePropertyChange(this.name, null, value);
    }
}
