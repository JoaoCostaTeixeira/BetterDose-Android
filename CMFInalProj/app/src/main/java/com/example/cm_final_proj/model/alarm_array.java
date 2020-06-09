package com.example.cm_final_proj.model;

public class alarm_array {

    String key;
    alarm_model alarm;
    String image;
    int num;

    public alarm_array(String key, alarm_model alarm, String image, int num) {
        this.key = key;
        this.alarm = alarm;
        this.image = image;
        this.num = num;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public alarm_model getAlarm() {
        return alarm;
    }

    public void setAlarm(alarm_model alarm) {
        this.alarm = alarm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
