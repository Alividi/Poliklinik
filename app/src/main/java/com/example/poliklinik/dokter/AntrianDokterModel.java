package com.example.poliklinik.dokter;

public class AntrianDokterModel {
    String idAntrian;
    String urutan;
    String nmPasien;
    String nmDokter;
    String status;

    public AntrianDokterModel() {

    }

    public AntrianDokterModel(String idAntrian, String urutan, String nmPasien, String nmDokter, String status) {
        this.idAntrian = idAntrian;
        this.urutan = urutan;
        this.nmPasien = nmPasien;
        this.nmDokter = nmDokter;
        this.status = status;
    }

    public String getIdAntrian() {
        return idAntrian;
    }

    public void setIdAntrian(String idAntrian) {
        this.idAntrian = idAntrian;
    }

    public String getUrutan() {
        return urutan;
    }

    public void setUrutan(String urutan) {
        this.urutan = urutan;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
