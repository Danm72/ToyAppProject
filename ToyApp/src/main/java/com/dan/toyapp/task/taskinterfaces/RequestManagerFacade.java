package com.dan.toyapp.task.taskinterfaces;

/**
 * Created by danmalone on 12/11/2013.
 */
public interface RequestManagerFacade {
    public void createSyncTask(String param, boolean reverse);
    public boolean getProgressState();
}
