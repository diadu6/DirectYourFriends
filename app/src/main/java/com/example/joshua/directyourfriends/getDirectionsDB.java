package com.example.joshua.directyourfriends;

/**
 * Created by Joshua on 4/26/2016.
 */
public class getDirectionsDB {


    //initialization

    private int id;
    private String search;

    public getDirectionsDB() {}

    public getDirectionsDB(String search) {
        this.search = search;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
