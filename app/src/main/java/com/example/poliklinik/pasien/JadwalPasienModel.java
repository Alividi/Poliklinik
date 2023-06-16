package com.example.poliklinik.pasien;

public class JadwalPasienModel {
    String kalenderId;
    String kalenderJam;
    String kalenderNmPasien;
    String kalenderNmDokter;
    String kalenderKategori;
    String kalenderStatus;

    public JadwalPasienModel() {

    }

    public JadwalPasienModel(String kalenderId, String kalenderJam, String kalenderNmPasien, String kalenderNmDokter, String kalenderKategori, String kalenderStatus) {
        this.kalenderId = kalenderId;
        this.kalenderJam = kalenderJam;
        this.kalenderNmPasien = kalenderNmPasien;
        this.kalenderNmDokter = kalenderNmDokter;
        this.kalenderKategori = kalenderKategori;
        this.kalenderStatus = kalenderStatus;
    }

    public String getKalenderId() {
        return kalenderId;
    }

    public void setKalenderId(String kalenderId) {
        this.kalenderId = kalenderId;
    }

    public String getKalenderJam() {
        return kalenderJam;
    }

    public void setKalenderJam(String kalenderJam) {
        this.kalenderJam = kalenderJam;
    }

    public String getKalenderNmPasien() {
        return kalenderNmPasien;
    }

    public void setKalenderNmPasien(String kalenderNmPasien) {
        this.kalenderNmPasien = kalenderNmPasien;
    }

    public String getKalenderNmDokter() {
        return kalenderNmDokter;
    }

    public void setKalenderNmDokter(String kalenderNmDokter) {
        this.kalenderNmDokter = kalenderNmDokter;
    }

    public String getKalenderKategori() {
        return kalenderKategori;
    }

    public void setKalenderKategori(String kalenderKategori) {
        this.kalenderKategori = kalenderKategori;
    }

    public String getKalenderStatus() {
        return kalenderStatus;
    }

    public void setKalenderStatus(String kalenderStatus) {
        this.kalenderStatus = kalenderStatus;
    }
}
