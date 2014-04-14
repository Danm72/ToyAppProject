package com.dan.toyapp.entity;

import com.dan.toyapp.entity.interfaces.DisplayFacade;

/**
 * Created by danmalone on 12/10/2013.
 */
public class City implements DisplayFacade {
    String id; // A unique identiﬁer for the object
    String country; // Two letter country code, e.g., “IE”
    String region; // Country speciﬁc region
    String city;  //The name of a city, e.g., “Dublin”
    float latitude;  //number Decimal representation: -90º to +90º
    String comment;
    float longitude; // Decimal representation: -180º to +180º

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //e.g., 53.34978

    // e.g., -6.260316

    City(String id, String country, String region, String city, float latitude, float longitude, String comment) {
        this.id = id;
        this.country = country;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return id +
                "," + country + '\'' +
                ",'" + region + '\'' +
                ",'" + city + '\'' +
                "," + latitude +
                "," + longitude;
    }

    public City() {

    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getNameToDisplay() {
        return getCity();
    }

    @Override
    public String getSubtitle() {
        return "Comment: " + comment;
    }

    @Override
    public String getIdOfItem() {
        return getId();
    }

    @Override
    public String getFirstItem() {
        return getLatitude()+"";
    }

    @Override
    public String getSecondItem() {
        return getLongitude()+"";
    }

    @Override
    public String getExtra() {
        return getRegion();
    }
}
