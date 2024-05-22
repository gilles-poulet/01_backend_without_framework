package ue1104.iramps.be.Model.BL;

import java.util.ArrayList;

public class Section {    
    public static ArrayList<Section> listSection = new ArrayList<Section>();
    private final int id;
    private String nom;

    /**
     * Classe permettant la modélisation d'une section (de cours).
     * 
     * @param id  Identifiant de la section
     * @param nom Nom de la section
     */
    public Section(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public static Section setSection(int id, String nom ){
        Section section = new Section(id, nom);
        if (! listSection.contains(section)) {
            listSection.add(section);
        }
        return section;
    }

    public static Section deletesection(int id){
        Section section = new Section(id, "");
        if (! listSection.contains(section)) {
            listSection.remove(section);
        }
        return section;
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

    public boolean equals(Section other){
        if (other.id == this.id) return true;
        return false;
    }
}