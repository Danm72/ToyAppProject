package com.dan.toyapp.task;

import com.dan.toyapp.adapter.BufferAdapter;
import com.dan.toyapp.task.taskinterfaces.Observer;
import com.dan.toyapp.task.taskinterfaces.RequestManagerFacade;
import com.dan.toyapp.task.taskinterfaces.Subject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dan.toyapp.constants.Constants.COUNTRY;
import static com.dan.toyapp.constants.Constants.CITY;



/**
 * Created by danmalone on 02/11/2013.
 */
public class RequestSubject implements RequestManagerFacade, Subject {

    public boolean _inProgress = false;

    String paramCode;
    private boolean updateAvailable;
    String URL;
    private List<Observer> observers;
    Object temporaryReturnData;


    public RequestSubject(String paramCode, String URL, BufferAdapter adapter) {
        this.URL = URL;
        observers = new ArrayList();

        this.paramCode = paramCode;
        new ListObserver(this, adapter);

        initialiseList();
    }

    /**
     * This method controls calls to the API and responses back to the buffer
     * It uses the subject.notify to notify all observerables of an update
     * @param param
     * @param reverse
     */
    @Override
    synchronized public void createSyncTask(String param, final boolean reverse) {
        updateAvailable = false;
        RequestParams params = new RequestParams();
        params.add("id", param);
        if (URL.contains("cities.php"))
            params.add(COUNTRY, paramCode);
        else if (URL.contains("people.php"))
            params.add(CITY, paramCode);
        if (reverse) {
            params.add("reverse", "1");
        }

        _inProgress = true;
        RequestHub.get(URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);

                _inProgress = false;
                Map<Boolean, Object> map = new HashMap<>();
                map.put(reverse, response);

                temporaryReturnData = map;
                updateAvailable = true; //update flag is tripped
                notifyObservers(); //Notify without params, this will allow the observers to pull the data.

            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                super.onFailure(e, errorResponse);
            }

        });
    }

    @Override
    public boolean getProgressState() {
        return _inProgress;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o); //observers are added to this subject
    }

    @Override
    public void removeObserver(Observer o) {
        int j = observers.indexOf(o);
        if (j >= 0) {
            observers.remove(j);
        }
    }

    /**
     * goes through all observers attached to this subject and informs them an element is ready to be collected
     */
    @Override
    public void notifyObservers() {
        //   setUpdateAvailable(true);
        for (Observer obs : observers) {
            obs.getData();
        }
    }


    /**
     * goes through all observers attached to this subject and sends them a reference of this class
     */
    @Override
    public void notifyObservers(Subject subject) {
        for (Observer obs : observers) {
            obs.update(subject);
        }
    }

    /**
     * used by observers to pull
     * @return
     */

    @Override
    public Object getState() {
        return temporaryReturnData;
    }

    /**
     * used by observers to confirm a pull is possible
     * @return
     */
    @Override
    public boolean hasUpdate() {
        return updateAvailable;
    }

    private void initialiseList() {
        RequestParams params = new RequestParams();
        params.add("id", "0");
        if (URL.contains("cities.php")){
            params.add(COUNTRY, paramCode);
        }
        else if (URL.contains("people.php")){
            params.add(CITY, paramCode);
        }
        RequestHub.get(URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);

                Map<Boolean, Object> map = new HashMap<>();
                map.put(false, response);
                temporaryReturnData = map;
                notifyObservers();
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                super.onFailure(e, errorResponse);
            }
        });
    }
}
