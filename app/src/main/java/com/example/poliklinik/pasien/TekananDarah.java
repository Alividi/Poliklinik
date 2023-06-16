package com.example.poliklinik.pasien;

public class TekananDarah {
    long id;
    String tanggal_tekanan_darah;
    String waktu_tekanan_darah;
    String batas_atas_tekanan;
    String batas_bawah_tekanan;
    String komentar_tekanan_darah;
    public TekananDarah() {

    }
    public TekananDarah(long id, String tanggal_tekanan_darah, String waktu_tekanan_darah,
                        String batas_atas_tekanan, String batas_bawah_tekanan, String komentar_tekanan_darah) {
        this.id = id;
        this.tanggal_tekanan_darah = tanggal_tekanan_darah;
        this.waktu_tekanan_darah = waktu_tekanan_darah;
        this.batas_atas_tekanan = batas_atas_tekanan;
        this.batas_bawah_tekanan = batas_bawah_tekanan;
        this.komentar_tekanan_darah = komentar_tekanan_darah;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTanggal_tekanan_darah() {
        return tanggal_tekanan_darah;
    }
    public void setTanggal_tekanan_darah(String tanggal_tekanan_darah) {
        this.tanggal_tekanan_darah = tanggal_tekanan_darah;
    }
    public String getWaktu_tekanan_darah() {
        return waktu_tekanan_darah;
    }
    public void setWaktu_tekanan_darah(String waktu_tekanan_darah) {
        this.waktu_tekanan_darah = waktu_tekanan_darah;
    }
    public String getBatas_atas_tekanan() {
        return batas_atas_tekanan;
    }
    public void setBatas_atas_tekanan(String batas_atas_tekanan) {
        this.batas_atas_tekanan = batas_atas_tekanan;
    }
    public String getBatas_bawah_tekanan() {
        return batas_bawah_tekanan;
    }
    public void setBatas_bawah_tekanan(String batas_bawah_tekanan) {
        this.batas_bawah_tekanan = batas_bawah_tekanan;
    }
    public String getKomentar_tekanan_darah() {
        return komentar_tekanan_darah;
    }
    public void setKomentar_tekanan_darah(String komentar_tekanan_darah) {
        this.komentar_tekanan_darah = komentar_tekanan_darah;
    }
    public String toString() {
        return id + " | " + tanggal_tekanan_darah;
    }
}