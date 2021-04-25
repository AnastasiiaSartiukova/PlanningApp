package com.example.planning;

import java.util.ArrayList;

public class Project {

    private int pID;
    private String pName;
    private String pDate;
    private String pPlace;

    private ArrayList<String> pEmails;


    public Project(int id, String name, String date, String place, ArrayList<String> emails){

        pID = id;
        pName = name;
        pDate = date;
        pPlace = place;

        pEmails = new ArrayList<String>();
        pEmails.addAll(emails);

    }

}
