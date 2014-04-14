package com.dan.toyapp.task;

import com.dan.toyapp.adapter.BufferAdapter;
import com.dan.toyapp.entity.interfaces.DisplayFacade;
import com.dan.toyapp.entity.abstractEntity.AbstractEntityFactory;
import com.dan.toyapp.entity.CityEntityFactory;
import com.dan.toyapp.entity.PersonEntityFactory;
import com.dan.toyapp.task.taskinterfaces.Observer;
import com.dan.toyapp.task.taskinterfaces.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by danmalone on 14/12/2013.
 */
public class ListObserver implements Observer {
    private Subject dataSubject;
    public static final boolean BACK = true;
    public static final boolean FRONT = false;
    BufferAdapter adapter;
    AbstractEntityFactory factory;

    /**
     * When a subject notifies this method is called,
     * The instance of the subject is used to supply data to update the listview
     *
     * @param update
     */
    @Override
    public void update(Subject update) {
        try {
            HashMap<Boolean, Object> response = (HashMap<Boolean, Object>) update.getState();
            parseResponse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used when the observer is pulling instead of having the data pushed to it
     */

    @Override
    public void getData() {
        try {
            HashMap<Boolean, Object> response = (HashMap<Boolean, Object>) dataSubject.getState();
            parseResponse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ListObserver(Subject subject, BufferAdapter adapter) {
        this.adapter = adapter;
        this.dataSubject = subject;
        dataSubject.registerObserver(this);
    }

    void addItemsToList(boolean isFront, List<DisplayFacade> cities) {
        if (!isFront) {
            Collections.reverse(cities);
            for (DisplayFacade city : cities) {
                adapter.addToFront(city);
            }
        } else if (isFront) {
            for (DisplayFacade city : cities) {
                adapter.addToEnd(city);
            }
        }
    }

    void parseResponse(Map<Boolean, Object> response) throws JSONException {//
        response.keySet();
        for (Boolean key : response.keySet()) {
            JSONObject object = (JSONObject) response.get(key);
            JSONArray array;
            array = object.getJSONArray("result");
            if (key) {
                List<DisplayFacade> cities = parseJsonArray(array);
                addItemsToList(FRONT, cities);
            } else {
                List<DisplayFacade> cities = parseJsonArray(array);
                addItemsToList(BACK, cities);
            }
        }
    }

    public List<DisplayFacade> parseJsonArray(JSONArray jsonArray) {
        List<DisplayFacade> cities = new LinkedList<>();

        try {
            DisplayFacade temporaryCity = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (object.has("surname")) {
                    factory = new PersonEntityFactory();
                    temporaryCity = factory.createProduct(object);
                } else {
                    factory = new CityEntityFactory();
                    temporaryCity = factory.createProduct(object);
                }


                cities.add(temporaryCity);
            }
            addItemsToList(true, cities);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return cities;
    }

}
