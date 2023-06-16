package com.example.poliklinik.dokter;

public class PasienModel {
    String id;
    String nama;
    String username;
    String password;
    String aktor;
    String nim;
    String alamat;
    String bb;
    String tb;

    public PasienModel() {

    }

    public PasienModel(String id, String nama, String username, String password, String aktor, String nim, String alamat, String bb, String tb) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.aktor = aktor;
        this.nim = nim;
        this.alamat = alamat;
        this.bb = bb;
        this.tb = tb;
    }

    public PasienModel(String nama) {
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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }

    public String getTb() {
        return tb;
    }

    public void setTb(String tb) {
        this.tb = tb;
    }
}
