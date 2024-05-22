package ue1104.iramps.be.Model.DAL.Postgres.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Status;
import ue1104.iramps.be.Model.DAL.Interfaces.IStatusDAO;;

public class StatusDAO implements IStatusDAO {
    Connection connexion;
    PreparedStatement insertStatus;   
    PreparedStatement updateStatus;
    PreparedStatement deleteStatus;
    PreparedStatement getIDStatus;
    PreparedStatement getStatus;
    PreparedStatement getStatusByID;


    public StatusDAO(Connection connexion) {
        try {
            this.connexion =connexion;
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Status (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nom VARCHAR(30) UNIQUE)");
            } catch (SQLException e) {
                // La table existe déjà. Log pour le cas où.
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertStatus = this.connexion.prepareStatement("INSERT into Status (nom) VALUES (?)");
            this.updateStatus = this.connexion.prepareStatement("UPDATE Status SET nom=? WHERE id=?");
            this.deleteStatus = this.connexion.prepareStatement("DELETE FROM Status WHERE id=?");
            this.getIDStatus = this.connexion.prepareStatement("SELECT id FROM Status WHERE nom=?");
            this.getStatus = this.connexion.prepareStatement("SELECT id,nom FROM Status");
            this.getStatusByID = this.connexion.prepareStatement("SELECT id,nom FROM Status where id = ?");

            ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean close() {
        boolean ret = true;
        if (this.updateStatus != null) {
            try {
                this.updateStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getIDStatus != null) {
            try {
                this.getIDStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.deleteStatus != null) {
            try {
                this.deleteStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.getStatus != null) {
            try {
                this.getStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.insertStatus != null) {
            try {
                this.insertStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Status> getAllStatus() {
        ArrayList<Status> listeStatus = new ArrayList<Status>();
        try {
            ResultSet set = this.getStatus.executeQuery();
            while (set.next()) {                
                Status status = Status.setStatus(set.getInt(1), set.getString(2));
                listeStatus.add(status);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeStatus;

    }

    @Override
    public int getIDStatus(String nom) {
        int id = -1;
        try {
            this.getIDStatus.setString(1, nom);
            ResultSet set = this.getIDStatus.executeQuery();
            while (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public boolean updateStatus(int id, String nom) {
        try {        
            this.updateStatus.setString(1, nom);
            this.updateStatus.setInt(2, id);    
            if (this.updateStatus.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteStatus(int id) {
        try {
            this.deleteStatus.setInt(1, id);
            if (this.deleteStatus.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addStatus(String nom) {
        try {
            this.insertStatus.setString(1, nom);
            if (this.insertStatus.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Status getStatus(int id) {
        try {
            this.getStatusByID.setInt(1, id); 
            ResultSet set = this.getStatusByID.executeQuery();
            while (set.next()) {
                Status status = Status.setStatus(set.getInt(1), set.getString(2));
                return status;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
