package com.example.cm_final_proj.model;

public class med_array {
    String key;
    Medicamentos med;
    public med_array(String key, Medicamentos med){
        this.key = key;
        this.med = med;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Medicamentos getMed() {
        return med;
    }

    public void setMed(Medicamentos med) {
        this.med = med;
    }
}
