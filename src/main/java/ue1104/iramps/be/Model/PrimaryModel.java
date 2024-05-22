package ue1104.iramps.be.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Cours;
import ue1104.iramps.be.Model.BL.JointureComposee;
import ue1104.iramps.be.Model.BL.Personne;
import ue1104.iramps.be.Model.BL.Section;
import ue1104.iramps.be.Model.BL.Status;
import ue1104.iramps.be.Model.DAL.AbstractDAOFactory;
import ue1104.iramps.be.Model.DAL.EnumDAOType;
import ue1104.iramps.be.Model.DAL.Interfaces.ICoursDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.ICoursPersonneDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.IDAOFactory;
import ue1104.iramps.be.Model.DAL.Interfaces.IPersonneDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.ISectionDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.IStatusDAO;

public class PrimaryModel implements IModel{
    private ISectionDAO iSectionDAO;
    private ICoursDAO iCoursDAO;
    private IPersonneDAO iPersonneDAO;
    private IStatusDAO iStatusDAO;
    private ICoursPersonneDAO iCoursPersonneDAO;
    private IDAOFactory postgresDAOFactory;
    public static PrimaryModel instance;

    private PrimaryModel() throws IOException, SQLException, ClassNotFoundException{
        this.postgresDAOFactory = AbstractDAOFactory.createFactory(EnumDAOType.POSTGRES);
        this.iSectionDAO = postgresDAOFactory.createSectionDAO();
        this.iStatusDAO = postgresDAOFactory.createStatusDAO();
        this.iCoursDAO = postgresDAOFactory.createCoursDAO();
        this.iPersonneDAO = postgresDAOFactory.createPersonneDAO();
        this.iCoursPersonneDAO = postgresDAOFactory.createCoursPersonneDAO();
    }

    public static PrimaryModel getInstance() throws IOException, SQLException, ClassNotFoundException{
        if (instance == null) {
            instance = new PrimaryModel();
        }
        return instance;
    }

    public ArrayList<Section> getAllSection(){
        ArrayList<Section> sections = this.iSectionDAO.getSections();
        return sections;   
    }

    public Section getSection(String sectionName){
        int id = this.iSectionDAO.getIDSection(sectionName);
        return new Section(id,sectionName);
    }

    @Override
    public Section getSection(int sectionID) {
       return this.iSectionDAO.getSection(sectionID);
    }

    @Override
    public boolean deleteSection(String id) {
        int sectionID = Integer.parseInt(id);
        boolean ret =  this.iSectionDAO.deleteSection(sectionID);
        if (ret) {
            Section.deletesection(sectionID);
        }
        return ret;
    }

    @Override
    public boolean updateSection(String id, String nom) {
        int sectionID = Integer.parseInt(id);
        boolean ret =  this.iSectionDAO.updateSection(sectionID, nom);
        if (ret) {
            Section.setSection(sectionID, nom);
        }
       return  ret;
    }

    @Override
    public boolean insertSection(String nom) {
        boolean ret =  this.iSectionDAO.addSection(nom);
        if (ret) {
           this.getSection(nom);
        }
        return ret;
    }

    @Override
    public boolean close() {
        boolean ret = this.iSectionDAO.close();
        ret = (ret && this.iStatusDAO.close());
        ret = (ret && this.iCoursDAO.close());
        ret = (ret && this.iPersonneDAO.close());
        ret = (ret && this.iCoursPersonneDAO.close());
        ret = (ret && this.postgresDAOFactory.close());
        return ret;
    }

    @Override
    public ArrayList<Status> getAllStatus() {
        return this.iStatusDAO.getAllStatus();
    }

    @Override
    public Status getStatus(String StatusName) {
        int id = this.iStatusDAO.getIDStatus(StatusName);
        return Status.setStatus(id, StatusName);
    }

    @Override
    public Status getStatus(int StatusID) {
        return this.iStatusDAO.getStatus(StatusID);
    }

    @Override
    public boolean deleteStatus(String id) {
        int statusID = Integer.parseInt(id);
        boolean ret =  this.iStatusDAO.deleteStatus(statusID);
        if (ret) {
            Status.deleteStatus(statusID);
        }
        return ret;
    }

    @Override
    public boolean updateStatus(String id, String nom) {
        int statusID = Integer.parseInt(id);
        boolean ret =  this.iStatusDAO.updateStatus(statusID, nom);
        if (ret) {
            Status.setStatus(statusID, nom);
        }
        return ret;
    }

    @Override
    public boolean insertStatus(String nom) {
        boolean ret =  this.iStatusDAO.addStatus(nom);
        if (ret) {
           this.getStatus(nom);
        }
        return ret;
    }

    @Override
    public ArrayList<Personne> getAllPersonne() {
        return this.iPersonneDAO.getAllPersonnes();
    }

    @Override
    public Personne getPersonne(String personneLastName, String personneFirstname) {
        int id = this.iPersonneDAO.getIDPersonne(personneLastName, personneFirstname);
        return Personne.setPersonne(id, personneLastName, personneFirstname);
    }

    @Override
    public Personne getPersonne(int personneID) {
        return this.iPersonneDAO.getPersonne(personneID);
    }

    @Override
    public boolean deletePersonne(String id) {
        int personneID = Integer.parseInt(id);
        boolean ret =  this.iPersonneDAO.deletePersonne(personneID);
        if (ret) {
            Personne.deletePersonne(personneID);
        }
        return ret;
    }

    @Override
    public boolean updatePersonne(String id, String nom, String prenom) {
        int personneID = Integer.parseInt(id);
        boolean ret =  this.iPersonneDAO.updatePersonne(personneID, nom, prenom);
        if (ret) {
            Personne.setPersonne(personneID, nom, prenom);
        }
        return ret;
    }

    @Override
    public boolean insertPersonne(String nom, String prenom) {
        boolean ret =  this.iPersonneDAO.addPersonne(nom, prenom);
        if (ret) {
           this.getPersonne(nom, prenom);
        }
        return ret;
    }
    

    @Override
    public ArrayList<Cours> getAllCours() {
        return this.iCoursDAO.getAllCours();
    }

    @Override
    public Cours getCours(String CoursName) {
        int id = this.iCoursDAO.getIDCours(CoursName);
        return Cours.setCours(id, CoursName);
    }

    @Override
    public Cours getCours(int CoursID) {
        return this.iCoursDAO.getCours(CoursID);
    }

    @Override
    public boolean deleteCours(String id) {
        int coursID = Integer.parseInt(id);
        boolean ret =  this.iCoursDAO.deleteCours(coursID);
        if (ret) {
            Cours.deleteCours(coursID);
        }
        return ret;
    }

    @Override
    public boolean updateCours(String id, String nom) {
        int coursID = Integer.parseInt(id);
        boolean ret =  this.iCoursDAO.updateCours(coursID, nom);
        if (ret) {
            Cours.setCours(coursID, nom);
        }
        return ret;
    }

    @Override
    public boolean insertCours(String nom) {
        boolean ret =  this.iCoursDAO.addCours(nom);
        if (ret) {
           this.getCours(nom);
        }
        return ret;
    }

    @Override
    public boolean updateStatusPersonne(int idStatus, int idPersonne) {
        return  this.iPersonneDAO.addPersonneStatus(idPersonne, idStatus); 
    }

    @Override
    public boolean updateSectionCours(int idSection, int idCours) {
        return this.iCoursDAO.addSectionCours(idCours, idSection);
    }

    @Override
    public JointureComposee<Personne, Cours> getAllCoursPersonne() {
        return this.iCoursPersonneDAO.getAllCoursPersonne();
    }

    @Override
    public JointureComposee<Personne, Cours> getAllPersonneCours() {
        return this.iCoursPersonneDAO.getAllPersonneCours();
    }

    @Override
    public JointureComposee<Personne, Cours> getOnePersonneCours(int idPersonne) {
        return this.iCoursPersonneDAO.getOnePersonneCours(idPersonne);
    }

    @Override
    public JointureComposee<Personne, Cours> getOneCoursPersonne(int idCours) {
        return this.iCoursPersonneDAO.getOneCoursPersonne(idCours);
    }

    @Override
    public boolean deletePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin) {
        return this.iCoursPersonneDAO.deletePersonneCours(idPersonne, idCours, date_debut, date_fin);
    }

    @Override
    public boolean updatePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin,
                                       int newIdPersonne, int newIdCours, String newDateDebut, String newDateFin) {
        return this.iCoursPersonneDAO.updatePersonneCours(idPersonne, idCours, date_debut, date_fin, newIdPersonne, newIdCours, newDateDebut, newDateFin);
    }

    @Override
    public boolean insertPersonneCours(int idPersonne, int idCours, String date_debut, String date_fin) {
        return this.iCoursPersonneDAO.addPersonneCours(idPersonne, idCours, date_debut, date_fin);
    }

   
}
