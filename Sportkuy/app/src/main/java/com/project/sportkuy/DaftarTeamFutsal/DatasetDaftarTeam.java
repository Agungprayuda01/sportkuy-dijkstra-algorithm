package com.project.sportkuy.DaftarTeamFutsal;

import java.io.Serializable;

public class DatasetDaftarTeam implements Serializable {
    private String namakamu,namateam,waktu,tangal,jumlahanggota,lokasi,gambar,email,nohp,pid,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getGambar() {
        return gambar;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamakamu() {
        return namakamu;
    }

    public void setNamakamu(String namakamu) {
        this.namakamu = namakamu;
    }

    public String getNamateam() {
        return namateam;
    }

    public void setNamateam(String namateam) {
        this.namateam = namateam;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTangal() {
        return tangal;
    }

    public void setTangal(String tangal) {
        this.tangal = tangal;
    }

    public String getJumlahanggota() {
        return jumlahanggota;
    }

    public void setJumlahanggota(String jumlahanggota) {
        this.jumlahanggota = jumlahanggota;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public DatasetDaftarTeam(){

    }

//    public DatasetDaftarTeam(String namakamu, String namateam, String waktu, String tangal, String jumlahanggota, String lokasi, String gambar,String email,String nohp) {
//        this.namakamu = namakamu;
//        this.namateam = namateam;
//        this.waktu = waktu;
//        this.tangal = tangal;
//        this.jumlahanggota = jumlahanggota;
//        this.lokasi = lokasi;
//        this.gambar = gambar;
//        this.email = email;
//        this.nohp = nohp;
//
//    }


    public DatasetDaftarTeam(String namakamu, String namateam, String waktu, String tangal, String jumlahanggota,
                             String lokasi, String gambar, String email, String nohp, String pid,String status) {
        this.namakamu = namakamu;
        this.namateam = namateam;
        this.waktu = waktu;
        this.tangal = tangal;
        this.jumlahanggota = jumlahanggota;
        this.lokasi = lokasi;
        this.gambar = gambar;
        this.email = email;
        this.nohp = nohp;
        this.pid = pid;
        this.status = status;
    }
}