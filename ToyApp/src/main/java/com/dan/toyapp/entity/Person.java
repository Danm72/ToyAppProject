package com.dan.toyapp.entity;

import com.dan.toyapp.entity.interfaces.DisplayFacade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by danmalone on 12/10/2013.
 */
public class Person implements DisplayFacade {

    String id; // A unique identiﬁer for the object
    String surname; //Two letter country code, e.g., “IE”
    String forename; //Country speciﬁc region
    String comment;
    String julianDOB;  //Decimal representation: -90º to +90º
    //e.g., 53.34978
    //See Julian Day Number (JDN).
    int city; //The id of the person’s city in the City table


    Person(String id, String surname, String forename, int julianDOB, int city, String comment) throws ParseException {
        this.id = id;
        this.surname = surname;
        this.forename = forename;


        this.city = city;
        this.comment = comment;
        this.julianDOB = convertDate(julianDOB);
    }

    private String convertDate(int julianDOB) throws ParseException {
        String j = julianDOB + "";
        Date date = new SimpleDateFormat("Myydd").parse(j);
        String g = new SimpleDateFormat("dd.MM.yyyy").format(date);
        return g;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", forename='" + forename + '\'' +
                ", JulianDOB=" + julianDOB +
                ", city=" + city +
                '}';
    }

    public Person() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJulianDOB() {

        return julianDOB;
    }

    public void setJulianDOB(int julianDOB) {
        julianDOB = julianDOB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }


    @Override
    public String getNameToDisplay() {
        return getForename();
    }

    @Override
    public String getSubtitle() {
        return getComment();
    }

    @Override
    public String getIdOfItem() {
        return getId();
    }

    @Override
    public String getFirstItem() {
        return "CityID: " + getCity();
    }

    @Override
    public String getSecondItem() {
        return "DOB: " + getJulianDOB();
    }

    @Override
    public String getExtra() {
        return getSurname();
    }

}
