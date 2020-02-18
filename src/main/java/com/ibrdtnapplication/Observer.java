package main.java.com.ibrdtnapplication;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class Observer<T> implements PropertyChangeListener {
    abstract void update(T value);

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        try {
            this.update((T) propertyChangeEvent.getNewValue());
        } catch (Exception e) {}
    }
}
