package ue1104.iramps.be.Model.DAL.Interfaces;

import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Personne;

public interface IPersonneDAO {

    public ArrayList<Personne> getAllPersonnes();
    public int getIDPersonne(String nom, String prenom);    
    public Personne getPersonne(int id);
    public boolean updatePersonne(int id, String nom, String prenom);
    public boolean deletePersonne(int id);
    public boolean addPersonne(String nom, String prenom);
    public boolean addPersonneStatus(int idCours, int idStatus);
    public boolean close();
}
