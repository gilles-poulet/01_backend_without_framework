package ue1104.iramps.be.Model.BL;

import java.util.ArrayList;

public class Cours {
    private final int id;
    public static ArrayList<Cours> listCours = new ArrayList<Cours>();
    private String nom;
    private Section section;

    /**
     * Classe permettant la modélisation d'un cours.
     * 
     * @param id  Identifiant du cours
     * @param nom Nom du cours
     */
    private Cours(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public static Cours setCours(int id, String nom ){
        Cours Cours = new Cours(id, nom);
        if (! listCours.contains(Cours)) {
            listCours.add(Cours);
        }
        return Cours;
    }
    
    public static Cours deleteCours(int id){
        Cours Cours = new Cours(id, "");
        if (! listCours.contains(Cours)) {
            listCours.remove(Cours);
        }
        return Cours;
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
     * @return String retourne le nom du section
     */
    public String geSection() {
        return this.section.getNom();
    }

    /**
     * @return int retourne l'id de la section
     */
    public int geSectionID() {
        return this.section.getId();
    }

    /**
     * @param nom Le nom à mettre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @param nom Le section à mettre
     */
    public void setSection(Section section) {
        this.section = section;
    }

    public boolean equals(Cours other){
        if (other.id == this.id) return true;
        return false;
    }

}