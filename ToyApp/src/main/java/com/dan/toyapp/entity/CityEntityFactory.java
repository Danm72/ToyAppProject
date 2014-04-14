package com.dan.toyapp.entity;

import com.dan.toyapp.entity.abstractEntity.AbstractEntityFactory;
import com.dan.toyapp.entity.interfaces.DisplayFacade;

import org.json.JSONException;
import org.json.JSONObject;

public class CityEntityFactory extends AbstractEntityFactory {

    public DisplayFacade createProduct(JSONObject object) throws JSONException {

        return new City((String.valueOf(object.get("id"))),
                (String) object.get("country"),
                (String) object.get("region"),
                (String) object.get("city"),
                Float.parseFloat(((String.valueOf(object.get("latitude"))))),
                Float.parseFloat((String.valueOf(object.get("longitude")))),
                (String) object.get("comment"));
    }
}