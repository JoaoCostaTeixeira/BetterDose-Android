package com.example.cm_final_proj.model;

public class med_array_alarm {
    String key;
    Medicamentos med;
    int quant;

    public med_array_alarm(String key, Medicamentos med, int quant) {
        this.key = key;
        this.med = med;
        this.quant = quant;
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

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
