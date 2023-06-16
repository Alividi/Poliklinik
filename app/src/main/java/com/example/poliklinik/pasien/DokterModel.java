package com.example.poliklinik.pasien;

public class DokterModel {
    String id;
    String nama;
    String username;
    String password;
    String aktor;

    public DokterModel() {

    }

    public DokterModel(String id, String nama, String username, String password, String aktor) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.aktor = aktor;
    }

    public DokterModel(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAktor() {
        return aktor;
    }

    public void setAktor(String aktor) {
        this.aktor = aktor;
    }
}
