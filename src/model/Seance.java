package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Seance {
    private int idSeance;
    private int idMoniteur;
    private Integer idVehicule;
    private int idEleve;
    private LocalDate dateSeance;
    private int duree;
    private String typeSeance;
    private String statut;
    private String observations;

    // Getters et setters
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public int getIdMoniteur() {
        return idMoniteur;
    }

    public void setIdMoniteur(int idMoniteur) {
        this.idMoniteur = idMoniteur;
    }

    public Integer getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Integer idVehicule) {
        this.idVehicule = idVehicule;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public LocalDate getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
    
        @Override
    public String toString() {
        return "Seance{" +
                "idSeance=" + idSeance +
                ", idMoniteur=" + idMoniteur +
                ", idVehicule=" + idVehicule +
                ", idEleve=" + idEleve +
                ", dateSeance=" + dateSeance +
                ", duree=" + duree +
                ", typeSeance='" + typeSeance + '\'' +
                ", statut='" + statut + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
