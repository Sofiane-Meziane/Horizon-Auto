
package model;


public class Examen_Eleve {
    private int idExamEleve;
    private int idExamen;
    private int idEleve;
    private String resultat;
    private String commentaire;

    public int getIdExamEleve() {
        return idExamEleve;
    }

    public void setIdExamEleve(int idExamEleve) {
        this.idExamEleve = idExamEleve;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
}
