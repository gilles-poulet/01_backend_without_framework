package ue1104.iramps.be.Model.DAL.Postgres.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ue1104.iramps.be.Model.BL.Personne;
import ue1104.iramps.be.Model.BL.Status;
import ue1104.iramps.be.Model.DAL.Interfaces.IPersonneDAO;

public class PersonneDAO implements IPersonneDAO {
    Connection connexion;
    PreparedStatement insertPersonne;   
    PreparedStatement updatePersonne;
    PreparedStatement updatePersonneStatus;
    PreparedStatement deletePersonne;
    PreparedStatement getIDPersonne;
    PreparedStatement getPersonnes;
    PreparedStatement getPersonnesByID;


    public PersonneDAO(Connection connexion) {
        try {
            this.connexion =connexion;
            Statement statement = connexion.createStatement();
            try {
                statement.executeUpdate(new StringBuilder("CREATE TABLE IF NOT EXISTS Personne (")
                                        .append("id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,")
                                        .append("nom VARCHAR(30),")
                                        .append("prenom VARCHAR(30),")
                                        .append("status INT,")
                                        .append("FOREIGN KEY (status) REFERENCES status(id),")
                                        .append("UNIQUE(nom, prenom)")
                                        .append(")")
                                        .toString()
                );
            } catch (SQLException e) {
                // La table existe déjà. Log pour le cas où.
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertPersonne = this.connexion.prepareStatement("INSERT into Personne (nom, prenom) VALUES (?, ?)");           
            this.updatePersonne = this.connexion.prepareStatement("UPDATE Personne SET nom=?, prenom=? WHERE id=?");
            this.updatePersonneStatus = this.connexion.prepareStatement("UPDATE Personne SET status=? WHERE id=?");
            this.deletePersonne = this.connexion.prepareStatement("DELETE FROM Personne WHERE id=?");
            this.getIDPersonne = this.connexion.prepareStatement("SELECT id FROM Personne WHERE nom=? AND prenom=?");
            this.getPersonnes = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id,personne.nom,personne.prenom,personne.status,status.nom")
                                                            .append(" FROM Personne as personne")
                                                            .append(" LEFT JOIN status ON personne.status=status.id")
                                                            .toString()
                                                            );
            this.getPersonnesByID = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id,personne.nom,personne.prenom,personne.status,status.nom")
                                                            .append(" FROM Personne as personne")
                                                            .append(" LEFT JOIN status ON personne.status=status.id")
                                                            .append(" WHERE personne.id = ?")
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
        if (this.updatePersonne != null) {
            try {
                this.updatePersonne.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.updatePersonneStatus != null) {
            try {
                this.updatePersonneStatus.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }

        if (this.getIDPersonne != null) {
            try {
                this.getIDPersonne.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        if (this.deletePersonne != null) {
            try {
                this.deletePersonne.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.getPersonnes != null) {
            try {
                this.getPersonnes.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        
        if (this.insertPersonne != null) {
            try {
                this.insertPersonne.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Personne> getAllPersonnes() {
        ArrayList<Personne> listePersonne = new ArrayList<Personne>();
        try {
            ResultSet set = this.getPersonnes.executeQuery();
            while (set.next()) {
                Personne personne = Personne.setPersonne(set.getInt(1), set.getString(2), set.getString(3));
                personne.setStatus(Status.setStatus(set.getInt(4), set.getString(5)));
                listePersonne.add(personne);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listePersonne;

    }

    @Override
    public int getIDPersonne(String nom, String prenom) {
        int id = -1;
        try {
            this.getIDPersonne.setString(1, nom);
            this.getIDPersonne.setString(2, prenom);
            ResultSet set = this.getIDPersonne.executeQuery();
            while (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public boolean updatePersonne(int id, String nom, String prenom) {
        try {        
            this.updatePersonne.setString(1, nom);
            this.updatePersonne.setString(2, prenom);
            this.updatePersonne.setInt(3, id);    
            if (this.updatePersonne.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletePersonne(int id) {
        try {
            this.deletePersonne.setInt(1, id);
            if (this.deletePersonne.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addPersonne(String nom, String prenom) {
        try {
            this.insertPersonne.setString(1, nom);
            this.insertPersonne.setString(2, prenom);
            if (this.insertPersonne.executeUpdate() == 0) return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addPersonneStatus(int idCours, int idStatus) {
        try{
            this.updatePersonneStatus.setInt(1, idCours);
            this.updatePersonneStatus.setInt(2, idStatus);
            if (this.updatePersonneStatus.executeUpdate() == 0) return false;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    
    @Override
    public Personne getPersonne(int id) {        
        try {            
            this.getPersonnesByID.setInt(1, id);            
            ResultSet set = this.getPersonnesByID.executeQuery();
            while (set.next()) {
                Personne personne = Personne.setPersonne(set.getInt(1), set.getString(2), set.getString(3));
                personne.setStatus(Status.setStatus(set.getInt(4), set.getString(5)));
                return personne;
            }           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
