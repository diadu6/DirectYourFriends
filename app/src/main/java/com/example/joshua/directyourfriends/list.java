package com.example.joshua.directyourfriends;

/**
 * Created by Joshua on 4/7/2016.
 */

/**
 * Created by user on 4/5/2016.
 *
 */


//Retreive the data, throws to a string,
    //Creates variables in lists


public class list {

    private int id;
    private String name;

    public list() {}

    public list(String name) {
        this.name = name;
    }

    public list(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;    //used for add/edit spinner
    }
}
