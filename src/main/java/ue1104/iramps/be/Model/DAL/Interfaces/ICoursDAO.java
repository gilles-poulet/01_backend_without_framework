package ue1104.iramps.be.Model.DAL.Interfaces;

import java.util.ArrayList;
import ue1104.iramps.be.Model.BL.Cours;
public interface ICoursDAO {

    public ArrayList<Cours> getAllCours();    
    public int getIDCours(String nom);
    public Cours getCours(int id);
    public boolean updateCours(int id, String nom);
    public boolean deleteCours(int id);
    public boolean addCours(String nom);
    public boolean addSectionCours(int idCours, int idSection);
    public boolean close();
}
