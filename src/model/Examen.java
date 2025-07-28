package model;

import java.time.LocalDate;

public class Examen {
    private int idExamen;
    private String typeExamen;
    private LocalDate dateExamen;
    private String lieu;
    private int duree;

    // Getters et setters
    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public String getTypeExamen() {
        return typeExamen;
    }

    public void setTypeExamen(String typeExamen) {
        this.typeExamen = typeExamen;
    }

    public LocalDate getDateExamen() {
        return dateExamen;
    }

    public void setDateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}
