package com.dan.toyapp.entity;

import com.dan.toyapp.entity.interfaces.DisplayFacade;

/**
 * Created by danmalone on 18/10/2013.
 */
public class Country implements DisplayFacade {
    String name;
    String countryCode;

    public Country() {
    }

    public Country(String name, String countryCode) {
        this.countryCode = countryCode;
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return name +","+ countryCode;
    }

    @Override
    public String getNameToDisplay() {
        return getName();
    }

    @Override
    public String getSubtitle() {
        return "";
    }

    @Override
    public String getIdOfItem() {
        return "";
    }

    @Override
    public String getFirstItem() {
        return "";
    }

    @Override
    public String getSecondItem() {
        return "";
    }

    @Override
    public String getExtra() {
        return getCountryCode();
    }
}
