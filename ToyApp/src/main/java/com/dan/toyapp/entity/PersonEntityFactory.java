package com.dan.toyapp.entity;

import com.dan.toyapp.entity.abstractEntity.AbstractEntityFactory;
import com.dan.toyapp.entity.interfaces.DisplayFacade;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class PersonEntityFactory extends AbstractEntityFactory {

    @Override
    public DisplayFacade createProduct(JSONObject object) throws JSONException, ParseException {
        return new Person((String.valueOf(object.get("id"))),
                (String) object.get("surname"),
                (String) object.get("forename"),
                Integer.parseInt((String.valueOf(object.get("dob")))),
                Integer.parseInt(String.valueOf(object.get("city"))),
                (String) object.get("comment"));    }
}