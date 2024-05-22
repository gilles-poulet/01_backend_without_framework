package ue1104.iramps.be.Model.DAL.Interfaces;

import ue1104.iramps.be.Model.BL.Cours;
import ue1104.iramps.be.Model.BL.JointureComposee;
import ue1104.iramps.be.Model.BL.Personne;

public interface ICoursPersonneDAO {
    /**
     * Récupère l'ensemble des cours pour l'ensemble des personnes existantes.
     * @return
     */
    public JointureComposee<Personne,Cours> getAllPersonneCours();

    /**
     * Récupère les cours pour une personne.
     * @param id
     * @return
     */
    public JointureComposee<Personne,Cours> getOnePersonneCours(int id); 
    
    /**
     * Récupère l'ensemble des personnes pour l'ensembles des cours existants.
     * @return
     */
    public JointureComposee<Personne,Cours> getAllCoursPersonne();

    /**
     * Récupère l'ensemble des personnes pour un cours.
     * @param id
     * @return
     */
    public JointureComposee<Personne,Cours> getOneCoursPersonne(int id);

    public boolean updatePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin,
                                       int newIdPersonne, int newIdCours, String newDateDebut, String newDateFin);
    public boolean deletePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin);
    public boolean addPersonneCours(int idPersonne, int idCours, String date_debut, String date_fin);
    public boolean close();
}
