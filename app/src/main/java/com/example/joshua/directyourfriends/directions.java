package com.example.joshua.directyourfriends;

/**
 * Created by Joshua on 4/7/2016.
 */

/**
 * Created by user on 4/5/2016.
 */






//Creates the lists




public class directions {

    //initialization

    private int directionsId;
    private int listId;
    private String name;
    private String notes;
    private String whoSent;

    //still necessary?

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    public directions() {
        name = "";
    }

    public directions(int listId, String name, String notes, String whoSent) {
        this.listId = listId;
        this.name = name;
        this.notes = notes;
        this.whoSent = whoSent;
    }

    public directions(int directionsId, int listId, String name, String notes, String whoSent) {
        this.directionsId = directionsId;
        this.listId = listId;
        this.name = name;
        this.notes = notes;
        this.whoSent = whoSent;
    }

    public int getId() {
        return directionsId;
    }

    public void setId(int taskId) {
        this.directionsId = directionsId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getwhoSent() {
        return whoSent;
    }

    public void setwhoSent(String whoSent) {
        this.whoSent = whoSent;
    }
}
