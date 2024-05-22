package ue1104.iramps.be.Model.DAL.Postgres.Sections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Section;
import ue1104.iramps.be.Model.DAL.Interfaces.ISectionDAO;;

public class SectionDAO implements ISectionDAO {
    Connection connexion;
    PreparedStatement insertSection;   
    PreparedStatement updateSection;
    PreparedStatement deleteSection;
    PreparedStatement getIDSection;
    PreparedStatement getSections;
    PreparedStatement getSectionByID;


    public SectionDAO(Connection connexion) {
        try {
            this.connexion =connexion;
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Section (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, nom VARCHAR(30) UNIQUE)");
            } catch (SQLException e) {
                // La table existe déjà. Log pour le cas où.
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertSection = this.connexion.prepareStatement("INSERT into Section (nom) VALUES (?)");
            this.updateSection = this.connexion.prepareStatement("UPDATE Section SET nom=? WHERE id=?");
            this.deleteSection = this.connexion.prepareStatement("DELETE FROM Section WHERE id=?");
            this.getIDSection = this.connexion.prepareStatement("SELECT id FROM Section WHERE nom=?");
            this.getSections = this.connexion.prepareStatement("SELECT id,nom FROM Section");
            this.getSectionByID = this.connexion.prepareStatement("SELECT id,nom FROM Section WHERE id = ?");

            ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean close() {
        boolean ret = true;
        if (this.updateSection != null) {
            try {
                this.updateSection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getIDSection != null) {
            try {
                this.getIDSection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.deleteSection != null) {
            try {
                this.deleteSection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.getSections != null) {
            try {
                this.getSections.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.insertSection != null) {
            try {
                this.insertSection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Section> getSections() {
        ArrayList<Section> listeSection = new ArrayList<Section>();
        try {
            ResultSet set = this.getSections.executeQuery();
            while (set.next()) {
                Section section = new Section(set.getInt(1), set.getString(2));
                listeSection.add(section);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeSection;

    }

    @Override
    public int getIDSection(String nom) {
        int id = -1;
        try {
            this.getIDSection.setString(1, nom);
            ResultSet set = this.getIDSection.executeQuery();
            while (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public boolean updateSection(int id, String nom) {
        try {        
            this.updateSection.setString(1, nom);
            this.updateSection.setInt(2, id);    
            if (this.updateSection.executeUpdate() == 0 ) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteSection(int id) {
        try {
            this.deleteSection.setInt(1, id);
            if (this.deleteSection.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addSection(String nom) {
        try {
            this.insertSection.setString(1, nom);
            if (this.insertSection.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Section getSection(int id) {
        try {
            this.getSectionByID.setInt(1, id); 
            ResultSet set = this.getSectionByID.executeQuery();
            while (set.next()) {
                Section section = new Section(set.getInt(1), set.getString(2));
                return section;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
