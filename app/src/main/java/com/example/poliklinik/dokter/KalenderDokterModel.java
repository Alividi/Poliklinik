package com.example.poliklinik.dokter;

public class KalenderDokterModel {
    String kalenderId;
    String kalenderJam;
    String kalenderNmPasien;
    String kalenderNmDokter;
    String kalenderKategori;
    String kalenderStatus;

    public KalenderDokterModel(){

    }

    public KalenderDokterModel(String kalenderId, String kalenderJam, String kalenderNmPasien, String kalenderNmDokter, String kalenderKategori, String kalenderStatus) {
        this.kalenderId = kalenderId;
        this.kalenderJam = kalenderJam;
        this.kalenderNmPasien = kalenderNmPasien;
        this.kalenderNmDokter = kalenderNmDokter;
        this.kalenderKategori = kalenderKategori;
        this.kalenderStatus = kalenderStatus;
    }

    public String getKalenderJam() {
        return kalenderJam;
    }

    public String getKalenderNmPasien() {
        return kalenderNmPasien;
    }

    public String getKalenderNmDokter() {
        return kalenderNmDokter;
    }

    public String getKalenderKategori() {
        return kalenderKategori;
    }

    public void setKalenderJam(String kalenderJam) {
        this.kalenderJam = kalenderJam;
    }

    public void setKalenderNmPasien(String kalenderNmPasien) {
        this.kalenderNmPasien = kalenderNmPasien;
    }

    public void setKalenderNmDokter(String kalenderNmDokter) {
        this.kalenderNmDokter = kalenderNmDokter;
    }

    public void setKalenderKategori(String kalenderKategori) {
        this.kalenderKategori = kalenderKategori;
    }

    public String getKalenderId() {
        return kalenderId;
    }

    public void setKalenderId(String kalenderId) {
        this.kalenderId = kalenderId;
    }

    public String getKalenderStatus() {
        return kalenderStatus;
    }

    public void setKalenderStatus(String kalenderStatus) {
        this.kalenderStatus = kalenderStatus;
    }
}