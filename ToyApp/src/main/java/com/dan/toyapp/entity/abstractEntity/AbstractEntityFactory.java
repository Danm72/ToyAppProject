package com.dan.toyapp.entity.abstractEntity;

import com.dan.toyapp.entity.interfaces.DisplayFacade;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by danmalone on 15/12/2013.
 */
public abstract class AbstractEntityFactory {

    public abstract DisplayFacade createProduct(JSONObject object) throws JSONException,ParseException;
}
