package com.project.sportkuy.turnamen;

public class User {
    String nama,namateam,nohp,email,pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamateam() {
        return namateam;
    }

    public void setNamateam(String namateam) {
        this.namateam = namateam;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String pid,String nama, String namateam, String nohp, String email) {
        this.pid = pid;
        this.nama = nama;
        this.namateam = namateam;
        this.nohp = nohp;
        this.email = email;
    }
}
