package com.example.poliklinik.dokter;

public class NotifikasiDokterModel {
    String id;
    String nmPasien;
    String nmDokter;
    String idRM;
    String tanggal;
    String statusRM;
    String statusDT;
    String statusPS;

    public NotifikasiDokterModel() {

    }

    public NotifikasiDokterModel(String id, String nmPasien, String nmDokter, String idRM, String tanggal, String statusRM, String statusDT, String statusPS) {
        this.id = id;
        this.nmPasien = nmPasien;
        this.nmDokter = nmDokter;
        this.idRM = idRM;
        this.tanggal = tanggal;
        this.statusRM = statusRM;
        this.statusDT = statusDT;
        this.statusPS = statusPS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNmPasien() {
        return nmPasien;
    }

    public void setNmPasien(String nmPasien) {
        this.nmPasien = nmPasien;
    }

    public String getNmDokter() {
        return nmDokter;
    }

    public void setNmDokter(String nmDokter) {
        this.nmDokter = nmDokter;
    }

    public String getIdRM() {
        return idRM;
    }

    public void setIdRM(String idRM) {
        this.idRM = idRM;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatusRM() {
        return statusRM;
    }

    public void setStatusRM(String statusRM) {
        this.statusRM = statusRM;
    }

    public String getStatusDT() {
        return statusDT;
    }

    public void setStatusDT(String statusDT) {
        this.statusDT = statusDT;
    }

    public String getStatusPS() {
        return statusPS;
    }

    public void setStatusPS(String statusPS) {
        this.statusPS = statusPS;
    }
}
