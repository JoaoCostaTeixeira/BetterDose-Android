package com.example.cm_final_proj.model;

public class Farmacia_model {
    private int id;
    private String nome;
    private String morada;

    public Farmacia_model(int id, String nome, String morada){
        this.id = id;
        this.nome = nome;
        this.morada = morada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
