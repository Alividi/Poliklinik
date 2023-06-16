package com.example.poliklinik.dokter;

public class JadwalKonsultasiDokterModel {
    String konsultasiId;
    String konsultasiJam;
    String konsultasiNmPasien;
    String konsultasiNmDokter;
    String konsultasiKategori;
    String konsultasiStatus;

    public JadwalKonsultasiDokterModel(){

    }

    public JadwalKonsultasiDokterModel(String konsultasiId, String konsultasiJam, String konsultasiNmPasien, String konsultasiNmDokter, String konsultasiKategori, String konsultasiStatus) {
        this.konsultasiId = konsultasiId;
        this.konsultasiJam = konsultasiJam;
        this.konsultasiNmPasien = konsultasiNmPasien;
        this.konsultasiNmDokter = konsultasiNmDokter;
        this.konsultasiKategori = konsultasiKategori;
        this.konsultasiStatus = konsultasiStatus;
    }

    public String getKonsultasiId() {
        return konsultasiId;
    }

    public void setKonsultasiId(String konsultasiId) {
        this.konsultasiId = konsultasiId;
    }

    public String getKonsultasiJam() {
        return konsultasiJam;
    }

    public void setKonsultasiJam(String konsultasiJam) {
        this.konsultasiJam = konsultasiJam;
    }

    public String getKonsultasiNmPasien() {
        return konsultasiNmPasien;
    }

    public void setKonsultasiNmPasien(String konsultasiNmPasien) {
        this.konsultasiNmPasien = konsultasiNmPasien;
    }

    public String getKonsultasiNmDokter() {
        return konsultasiNmDokter;
    }

    public void setKonsultasiNmDokter(String konsultasiNmDokter) {
        this.konsultasiNmDokter = konsultasiNmDokter;
    }

    public String getKonsultasiKategori() {
        return konsultasiKategori;
    }

    public void setKonsultasiKategori(String konsultasiKategori) {
        this.konsultasiKategori = konsultasiKategori;
    }

    public String getKonsultasiStatus() {
        return konsultasiStatus;
    }

    public void setKonsultasiStatus(String konsultasiStatus) {
        this.konsultasiStatus = konsultasiStatus;
    }
}
