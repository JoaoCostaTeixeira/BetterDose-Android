package com.example.cm_final_proj.model;

public class house_array
{
    String key;
    House house;
    public house_array(String key, House house) {
        this.key = key;
        this.house = house;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

}
