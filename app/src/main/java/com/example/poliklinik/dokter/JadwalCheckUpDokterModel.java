package com.example.poliklinik.dokter;

public class JadwalCheckUpDokterModel {
    String checkUpId;
    String checkUpJam;
    String checkUpNmPasien;
    String checkUpNmDokter;
    String checkUpKategori;
    String checkUpStatus;

    public JadwalCheckUpDokterModel() {

    }

    public JadwalCheckUpDokterModel(String checkUpId, String checkUpJam, String checkUpNmPasien, String checkUpNmDokter, String checkUpKategori, String checkUpStatus) {
        this.checkUpId = checkUpId;
        this.checkUpJam = checkUpJam;
        this.checkUpNmPasien = checkUpNmPasien;
        this.checkUpNmDokter = checkUpNmDokter;
        this.checkUpKategori = checkUpKategori;
        this.checkUpStatus = checkUpStatus;
    }

    public String getCheckUpId() {
        return checkUpId;
    }

    public void setCheckUpId(String checkUpId) {
        this.checkUpId = checkUpId;
    }

    public String getCheckUpJam() {
        return checkUpJam;
    }

    public void setCheckUpJam(String checkUpJam) {
        this.checkUpJam = checkUpJam;
    }

    public String getCheckUpNmPasien() {
        return checkUpNmPasien;
    }

    public void setCheckUpNmPasien(String checkUpNmPasien) {
        this.checkUpNmPasien = checkUpNmPasien;
    }

    public String getCheckUpNmDokter() {
        return checkUpNmDokter;
    }

    public void setCheckUpNmDokter(String checkUpNmDokter) {
        this.checkUpNmDokter = checkUpNmDokter;
    }

    public String getCheckUpKategori() {
        return checkUpKategori;
    }

    public void setCheckUpKategori(String checkUpKategori) {
        this.checkUpKategori = checkUpKategori;
    }

    public String getCheckUpStatus() {
        return checkUpStatus;
    }

    public void setCheckUpStatus(String checkUpStatus) {
        this.checkUpStatus = checkUpStatus;
    }
}
