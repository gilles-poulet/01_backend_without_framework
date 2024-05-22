package ue1104.iramps.be.Model.BL;

import java.util.ArrayList;

public class Status {
    public static ArrayList<Status> listStatus = new ArrayList<Status>();
    private final int id;
    private String nom;

    /**
     * Classe permettant la modélisation d'un Status.
     * 
     * @param id  Identifiant de la section
     * @param nom Nom de la section
     */
    private Status(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public static Status setStatus(int id, String nom ){
        Status status = new Status(id, nom);
        if (! listStatus.contains(status)) {
            listStatus.add(status);
        }
        return status;
    }

    public static Status deleteStatus(int id){
        Status status = new Status(id, "");
        if (! listStatus.contains(status)) {
            listStatus.remove(status);
        }
        return status;
    }

    /**
     * @return int retourne l'identifiant
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return String retourne le nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * @param nom Le nom à mettre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean equals(Status other){
        if (other.id == this.id) return true;
        return false;
    }
}