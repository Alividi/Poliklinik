package com.example.poliklinik.dokter;

public class ObatModel {
    String id;
    String nmObat;
    String sb;
    String hp;
    String hj;
    String stock;

    public ObatModel() {

    }

    public ObatModel(String nmObat) {
        this.nmObat = nmObat;
    }

    public ObatModel(String id, String nmObat, String sb, String hp, String hj, String stock) {
        this.id = id;
        this.nmObat = nmObat;
        this.sb = sb;
        this.hp = hp;
        this.hj = hj;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNmObat() {
        return nmObat;
    }

    public void setNmObat(String nmObat) {
        this.nmObat = nmObat;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
