package ue1104.iramps.be.Model;

import ue1104.iramps.be.Model.BL.Cours;
import ue1104.iramps.be.Model.BL.JointureComposee;
import ue1104.iramps.be.Model.BL.Personne;
import ue1104.iramps.be.Model.BL.Section;
import ue1104.iramps.be.Model.BL.Status;

import java.util.ArrayList;

public interface IModel {
    public ArrayList<Section> getAllSection();
    public Section getSection(String sectionName);
    public Section getSection(int sectionID);
    public boolean deleteSection(String id);
    public boolean updateSection(String id, String nom); 
    public boolean insertSection(String nom);

    public ArrayList<Status> getAllStatus();
    public Status getStatus(String StatusName);
    public Status getStatus(int StatusID);
    public boolean deleteStatus(String id);
    public boolean updateStatus(String id, String nom); 
    public boolean insertStatus(String nom);

    public ArrayList<Personne> getAllPersonne();
    public Personne getPersonne(String personneLastName, String personneFirstName);
    public Personne getPersonne(int personneID);
    public boolean deletePersonne(String id);
    public boolean updatePersonne(String id, String nom, String prenom); 
    public boolean insertPersonne(String nom, String prenom);
    public boolean updateStatusPersonne(int idStatus, int idPersonne);

    public ArrayList<Cours> getAllCours();
    public Cours getCours(String CoursName);
    public Cours getCours(int CoursID);
    public boolean deleteCours(String id);
    public boolean updateCours(String id, String nom); 
    public boolean insertCours(String nom);
    public boolean updateSectionCours(int idSection, int idCours);

    public JointureComposee<Personne,Cours> getAllCoursPersonne();
    public JointureComposee<Personne,Cours> getAllPersonneCours();
    public JointureComposee<Personne,Cours> getOnePersonneCours(int idPersonne);
    public JointureComposee<Personne,Cours> getOneCoursPersonne(int idCours);
    public boolean deletePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin);
    public boolean updatePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin,
                                       int newIdPersonne, int newIdCours, String newDateDebut, String newDateFin); 
    public boolean insertPersonneCours(int idPersonne, int idCours, String date_debut, String date_fin);

    public boolean close();
}
