package com.dan.toyapp.scrollers;

import android.util.Log;
import android.widget.AbsListView;

import com.dan.toyapp.entity.interfaces.DisplayFacade;
import com.dan.toyapp.task.taskinterfaces.RequestManagerFacade;

/**
 * Created by danmalone on 26/10/2013.
 */
public class ScrollListenerExtension implements AbsListView.OnScrollListener {

    private static String TAG = "ScrollListener";
    private int counter_forward = 0;
    private int counter_backwards = 0;
    int lastVisibleViewItem = 0;
    int id = 0;

    RequestManagerFacade getManager;

    public ScrollListenerExtension(RequestManagerFacade getManager) {
        this.getManager = getManager;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        boolean firstVisibleGreaterThanLastVisible = firstVisiblePosition > lastVisibleViewItem;

        int count = absListView.getCount();
        boolean endOfList = absListView.getLastVisiblePosition() == count - 1;
        boolean beginningOfList = firstVisiblePosition == 1;

        int visibleItemCount = absListView.getLastVisiblePosition() - firstVisiblePosition;
        if ((firstVisibleGreaterThanLastVisible || endOfList) && !getManager.getProgressState()) {
            scrollForward(firstVisiblePosition, visibleItemCount, count, absListView);
        } else if (beginningOfList && !getManager.getProgressState()) {
            scrollBack(firstVisiblePosition, visibleItemCount, count, absListView);
        } else if (!getManager.getProgressState()) {
            scrollBack(firstVisiblePosition, visibleItemCount, count, absListView);
        }

        lastVisibleViewItem = firstVisiblePosition;

        Log.d(TAG, "Counter Forwards: " + counter_forward + "Counter Backwards: " + counter_backwards);


    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private void scrollBack(int firstVisibleItem, int visibleItemCount, int totalItemCount, AbsListView absListView) {
        boolean distanceToBeginning = firstVisibleItem <= 10;
        boolean enoughIDsLeft = id - visibleItemCount >= 0;

        if (distanceToBeginning && enoughIDsLeft) {
            try {
                id = Integer.parseInt(((DisplayFacade) absListView.getItemAtPosition(0)).getIdOfItem());
                getManager.createSyncTask(id + "", true);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
            counter_backwards += 1;
        }
    }

    private void scrollForward(int firstVisibleItem, int visibleItemCount, int totalItemCount, AbsListView absListView) {
        boolean distanceToEnd = (firstVisibleItem + visibleItemCount) + 10 >= totalItemCount;

        if (distanceToEnd) {
            try {
                id = Integer.parseInt(((DisplayFacade) absListView.getItemAtPosition(totalItemCount - 1)).getIdOfItem());
                getManager.createSyncTask(id + "", false);
            } catch (Exception e) {
                Log.w(TAG, e);
                throw e;
            }
            counter_forward += 1;
        }
    }


}
