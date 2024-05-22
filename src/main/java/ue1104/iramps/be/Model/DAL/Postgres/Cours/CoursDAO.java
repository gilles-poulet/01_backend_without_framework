package ue1104.iramps.be.Model.DAL.Postgres.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Cours;
import ue1104.iramps.be.Model.BL.Section;
import ue1104.iramps.be.Model.DAL.Interfaces.ICoursDAO;


public class CoursDAO implements ICoursDAO {
    Connection connexion;
    PreparedStatement insertCours;   
    PreparedStatement updateCours;
    PreparedStatement updateCoursSection;
    PreparedStatement deleteCours;
    PreparedStatement getIDCours;
    PreparedStatement getCours;
    PreparedStatement getCoursByID;


    public CoursDAO(Connection connexion) {
        try {
            this.connexion =connexion;
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate(new StringBuilder("CREATE TABLE IF NOT EXISTS Cours (")
                                                            .append("id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,")
                                                            .append("nom VARCHAR(30) UNIQUE,")
                                                            .append("section INT,")
                                                            .append("FOREIGN KEY (section) REFERENCES section(id)")
                                                            .append(")")
                                                            .toString()
                                                            );
            } catch (SQLException e) {
                // La table existe déjà. Log pour le cas où.
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertCours = this.connexion.prepareStatement("INSERT into Cours (nom) VALUES (?)");
            this.updateCours = this.connexion.prepareStatement("UPDATE Cours SET nom=? WHERE id=?");
            this.updateCoursSection = this.connexion.prepareStatement("UPDATE Cours SET section=? WHERE id=?");
            this.deleteCours = this.connexion.prepareStatement("DELETE FROM Cours WHERE id=?");
            this.getIDCours = this.connexion.prepareStatement("SELECT id FROM Cours WHERE nom=?");
            this.getCours = this.connexion.prepareStatement(new StringBuilder("SELECT cours.id,cours.nom,cours.section,section.nom")
                                                            .append(" FROM Cours as cours")
                                                            .append(" LEFT JOIN section ON cours.section=section.id")
                                                            .toString()
                                                            );
            this.getCoursByID = this.connexion.prepareStatement(new StringBuilder("SELECT cours.id,cours.nom,cours.section,section.nom")
                                                            .append(" FROM Cours as cours")
                                                            .append(" LEFT JOIN section ON cours.section=section.id")
                                                            .append(" WHERE cours.id = ?")
                                                            .toString()
                                                            );

            ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean close() {
        boolean ret = true;
        if (this.updateCours != null) {
            try {
                this.updateCours.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.updateCoursSection != null) {
            try {
                this.updateCoursSection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getIDCours != null) {
            try {
                this.getIDCours.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.deleteCours != null) {
            try {
                this.deleteCours.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.getCours != null) {
            try {
                this.getCours.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.insertCours != null) {
            try {
                this.insertCours.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Cours> getAllCours() {
        ArrayList<Cours> listeCours = new ArrayList<Cours>();
        try {
            ResultSet set = this.getCours.executeQuery();
            while (set.next()) {
                Cours cours = Cours.setCours(set.getInt(1), set.getString(2));
                cours.setSection(Section.setSection(set.getInt(3), set.getString(4)));
                listeCours.add(cours);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeCours;

    }

    @Override
    public int getIDCours(String nom) {
        int id = -1;
        try {
            this.getIDCours.setString(1, nom);
            ResultSet set = this.getIDCours.executeQuery();
            while (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public boolean updateCours(int id, String nom) {
        try {        
            this.updateCours.setString(1, nom);
            this.updateCours.setInt(2, id);    
            if (this.updateCours.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCours(int id) {
        try {
            this.deleteCours.setInt(1, id);
            if (this.deleteCours.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addCours(String nom) {
        try {
            this.insertCours.setString(1, nom);
            if (this.insertCours.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addSectionCours(int idCours, int idSection) {
        try{
            this.updateCoursSection.setInt(1, idCours);
            this.updateCoursSection.setInt(2, idSection);
            if (this.updateCoursSection.executeLargeUpdate() == 0) return false;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Cours getCours(int id) {        
        try {            
            this.getCoursByID.setInt(1, id);            
            ResultSet set = this.getCoursByID.executeQuery();
            while (set.next()) {
                Cours cours = Cours.setCours(set.getInt(1), set.getString(2));
                cours.setSection(Section.setSection(set.getInt(3), set.getString(4)));
                return cours;
            }           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
