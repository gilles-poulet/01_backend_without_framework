package ue1104.iramps.be.Model.BL;

import java.util.ArrayList;

public class Personne {    
    public static ArrayList<Personne> listPersonne = new ArrayList<Personne>();
    private final int id;
    private Status status;
    private String nom;
    private String prenom;

    /**
     * Classe permettant la modélisation d'une personne.
     * 
     * @param id  Identifiant de la section
     * @param nom Nom de la section
     */
    private Personne(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static Personne setPersonne(int id, String nom, String prenom){
        Personne Personne = new Personne(id, nom, prenom);
        if (! listPersonne.contains(Personne)) {
            listPersonne.add(Personne);
        }
        return Personne;
    }
    
    public static Personne deletePersonne(int id){
        Personne Personne = new Personne(id, "", "");
        if (! listPersonne.contains(Personne)) {
            listPersonne.remove(Personne);
        }
        return Personne;
    }

    /**
     * @return int retourne l'identifiant
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return String retourne le nom de famille
     */
    public String getNom() {
        return this.nom;
    }

        /**
     * @return String retourne le prenom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * @return String retourne le nom du status
     */
    public String geStatus() {
        return this.status.getNom();
    }

    /**
     * @return int retourne l'id du status
     */
    public int geStatusID() {
        return this.status.getId();
    }
    

    /**
     * @param nom Le nom à mettre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @param nom Le nom à mettre
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
 
    /**
     * @param nom Le status à mettre
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean equals(Personne other){
        if (other.id == this.id) return true;
        return false;
    }
}