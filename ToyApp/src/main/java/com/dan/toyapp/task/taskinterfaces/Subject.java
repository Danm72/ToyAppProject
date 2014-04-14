package com.dan.toyapp.task.taskinterfaces;

/**
 * Created by danmalone on 14/12/2013.
 */

public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();

    public void notifyObservers(Subject subject);

    public Object getState();

    public boolean hasUpdate();

}