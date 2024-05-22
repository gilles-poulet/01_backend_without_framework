package ue1104.iramps.be.Model.DAL.Postgres.Cours_Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.HashMap;

import ue1104.iramps.be.Model.BL.Cours;
import ue1104.iramps.be.Model.BL.JointureComposee;
import ue1104.iramps.be.Model.BL.Personne;
import ue1104.iramps.be.Model.DAL.Interfaces.ICoursPersonneDAO;

public class CoursPersonneDAO implements ICoursPersonneDAO{
    Connection connexion;
    PreparedStatement insertCoursPersonne;
    PreparedStatement updateCoursPersonne;
    PreparedStatement deleteCoursPersonne;
    PreparedStatement getAllPersonneCours;
    PreparedStatement getOnePersonneCours;
    PreparedStatement getAllCoursPersonne;
    PreparedStatement getOneCoursPersonne;

        public CoursPersonneDAO(Connection connexion) {
            try {
                this.connexion =connexion;
                Statement statement = connexion.createStatement();
                try {
                    statement.executeUpdate(new StringBuilder("CREATE TABLE IF NOT EXISTS Cours_Personne (")
                                                            .append("cours_id INT," )
                                                            .append("personne_id INT,")
                                                            .append("date_debut DATE,")
                                                            .append("date_fin DATE,")
                                                            .append("FOREIGN KEY (cours_id) REFERENCES Cours(id),")
                                                            .append("FOREIGN KEY (personne_id) REFERENCES Personne(personne_id),")
                                                            .append("PRIMARY KEY (personne_id,cours_id,date_debut,date_fin)")
                                                            .append(")")
                                                            .toString()
                    );


                } catch (SQLException e) {
                    // La table existe déjà. Log pour le cas où.
                    System.out.println(e.getMessage());
                }
                statement.close();
                insertCoursPersonne = this.connexion.prepareStatement(new StringBuilder("INSERT INTO Cours_Personne(cours_id,personne_id,date_debut,date_fin) VALUES (?,?,?,?)")
                                                                            .toString());
                                                                            
                updateCoursPersonne = this.connexion.prepareStatement(new StringBuilder("UPDATE Cours_Personne SET cours_id =? ,personne_id =?,date_debut =?,date_fin=?")
                                                                            .append(" WHERE cours_id =? AND personne_id =? AND date_debut =? AND date_fin=?")
                                                                            .toString());

                deleteCoursPersonne = this.connexion.prepareStatement(new StringBuilder("DELETE FROM Cours_Personne")
                                                                            .append(" WHERE cours_id =? AND personne_id =? AND date_debut =? AND date_fin=?")
                                                                            .toString());

                getAllPersonneCours = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id as pid, personne.nom as pnom, personne.prenom as pprenom, cours.id as cid, cours.nom as cnom, date_debut, date_fin")
                                                                            .append(" FROM Cours_Personne")
                                                                            .append(" RIGHT JOIN (SELECT id, nom, prenom FROM Personne) AS personne ON personne.id = Cours_Personne.personne_id")
                                                                            .append(" LEFT JOIN (SELECT id, nom FROM Cours) AS cours ON cours.id = Cours_Personne.cours_id")
                                                                            .toString());

                getOnePersonneCours = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id as pid, personne.nom as pnom, personne.prenom as pprenom, cours.id as cid, cours.nom as cnom, date_debut, date_fin")
                                                                            .append(" FROM Cours_Personne")
                                                                            .append(" RIGHT JOIN (SELECT id, nom, prenom FROM Personne) AS personne ON personne.id = Cours_Personne.personne_id")
                                                                            .append(" LEFT JOIN (SELECT id, nom FROM Cours) AS cours ON cours.id = Cours_Personne.cours_id")
                                                                            .append(" WHERE personne.id =?")
                                                                            .toString());

                getAllCoursPersonne = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id as pid, personne.nom as pnom, personne.prenom as pprenom, cours.id as cid, cours.nom as cnom, date_debut, date_fin")
                                                                            .append(" FROM Cours_Personne")
                                                                            .append(" LEFT JOIN (SELECT id, nom, prenom FROM Personne) AS personne ON personne.id = Cours_Personne.personne_id")
                                                                            .append(" RIGHT JOIN (SELECT id, nom FROM Cours) AS cours ON cours.id = Cours_Personne.cours_id")
                                                                            .toString());

                getOneCoursPersonne = this.connexion.prepareStatement(new StringBuilder("SELECT personne.id as pid, personne.nom as pnom, personne.prenom as pprenom, cours.id as cid, cours.nom as cnom, date_debut, date_fin")
                                                                            .append(" FROM Cours_Personne")
                                                                            .append(" LEFT JOIN (SELECT id, nom, prenom FROM Personne) AS personne ON personne.id = Cours_Personne.personne_id")
                                                                            .append(" RIGHT JOIN (SELECT id, nom FROM Cours) AS cours ON cours.id = Cours_Personne.cours_id")
                                                                            .append(" WHERE cours.id = ?")
                                                                            .toString());

            }  catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public boolean close() {
            boolean ret = true;
            if (this.updateCoursPersonne != null) {
                try {
                    this.updateCoursPersonne.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }
     
            if (this.getAllCoursPersonne != null) {
                try {
                    this.getAllCoursPersonne.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }  

            if (this.getAllPersonneCours != null) {
                try {
                    this.getAllPersonneCours.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }  

            if (this.getOneCoursPersonne != null) {
                try {
                    this.getOneCoursPersonne.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }
                 
            if (this.getOnePersonneCours != null) {
                try {
                    this.getOnePersonneCours.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }

            if (this.deleteCoursPersonne != null) {
                try {
                    this.deleteCoursPersonne.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }

            
            if (this.insertCoursPersonne != null) {
                try {
                    this.insertCoursPersonne.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    ret = false;
                }
            }
            return ret;
        }

        @Override
        public JointureComposee<Personne, Cours> getAllPersonneCours() {
            JointureComposee<Personne, Cours> jointure = new JointureComposee<>();
            try {
                ResultSet set = this.getAllPersonneCours.executeQuery();
                 while (set.next()) {
                    Personne personne = Personne.setPersonne(set.getInt("pid"), 
                                                        set.getString("pnom"),
                                                        set.getString("pprenom")                                                        
                                                        );
                    jointure.addFirstJoin(personne);
                    Cours cours = Cours.setCours(set.getInt("cid"),set.getString("cnom"));
                    jointure.addSecondJoin(cours);
                    if (! set.wasNull()){
                        HashMap<String,String> otherAttributes = new HashMap<>();
                        otherAttributes.put("date_debut",set.getString("date_debut"));
                        otherAttributes.put("date_fin",set.getString("date_fin"));
                        jointure.addotherAttributes(otherAttributes);
                    }
                 }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jointure;
        }

        @Override
        public JointureComposee<Personne, Cours> getOnePersonneCours(int id) {            
            JointureComposee<Personne, Cours> jointure = new JointureComposee<>();
            try {
                this.getOnePersonneCours.setInt(1, id);
                ResultSet set = this.getOnePersonneCours.executeQuery();
                 while (set.next()) {
                    Personne personne = Personne.setPersonne(set.getInt("pid"), 
                                                        set.getString("pnom"),
                                                        set.getString("pprenom")                                                        
                                                        );
                    jointure.addFirstJoin(personne);
                    Cours cours = Cours.setCours(set.getInt("cid"),set.getString("cnom"));
                    jointure.addSecondJoin(cours);
                    if (! set.wasNull()){
                        HashMap<String,String> otherAttributes = new HashMap<>();
                        otherAttributes.put("date_debut",set.getString("date_debut"));
                        otherAttributes.put("date_fin",set.getString("date_fin"));
                        jointure.addotherAttributes(otherAttributes);
                    }
                 }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jointure;
        }

        @Override
        public JointureComposee<Personne, Cours> getAllCoursPersonne() {
            JointureComposee<Personne, Cours> jointure = new JointureComposee<>();
            try {
                ResultSet set = this.getAllCoursPersonne.executeQuery();
                 while (set.next()) {
                    Personne personne = Personne.setPersonne(set.getInt("pid"), 
                                                        set.getString("pnom"),
                                                        set.getString("pprenom")                                                        
                                                        );
                    jointure.addFirstJoin(personne);
                    if (! set.wasNull()){
                        HashMap<String,String> otherAttributes = new HashMap<>();
                        otherAttributes.put("date_debut",set.getString("date_debut"));
                        otherAttributes.put("date_fin",set.getString("date_fin"));
                        jointure.addotherAttributes(otherAttributes);
                    }
                    Cours cours = Cours.setCours(set.getInt("cid"),set.getString("cnom"));
                    jointure.addSecondJoin(cours);
                    
                 }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jointure;
        }

        @Override
        public JointureComposee<Personne, Cours> getOneCoursPersonne(int id) {
            JointureComposee<Personne, Cours> jointure = new JointureComposee<>();
            try {
                this.getOneCoursPersonne.setInt(1, id);
                ResultSet set = this.getOneCoursPersonne.executeQuery();
                 while (set.next()) {
                    Personne personne = Personne.setPersonne(set.getInt("pid"), 
                                                        set.getString("pnom"),
                                                        set.getString("pprenom")                                                        
                                                        );
                    jointure.addFirstJoin(personne);
                    if (! set.wasNull()){
                        HashMap<String,String> otherAttributes = new HashMap<>();
                        otherAttributes.put("date_debut",set.getString("date_debut"));
                        otherAttributes.put("date_fin",set.getString("date_fin"));
                        jointure.addotherAttributes(otherAttributes);
                    }
                    Cours cours = Cours.setCours(set.getInt("cid"),set.getString("cnom"));
                    jointure.addSecondJoin(cours);
                    
                 }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return jointure;
        }

        @Override
        public boolean updatePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin, int newIdPersonne, int newIdCours,
                                           String newDateDebut, String newDateFin) {
            try {           
                this.updateCoursPersonne.setInt(1, newIdCours);
                this.updateCoursPersonne.setInt(2,newIdPersonne);
                this.updateCoursPersonne.setDate(3, Date.valueOf(newDateDebut));
                this.updateCoursPersonne.setDate(4, Date.valueOf(newDateFin));

                this.updateCoursPersonne.setInt(5, idCours);
                this.updateCoursPersonne.setInt(6,idPersonne);
                this.updateCoursPersonne.setDate(7, Date.valueOf(date_debut));
                this.updateCoursPersonne.setDate(8, Date.valueOf(date_fin));
                System.out.println(this.updateCoursPersonne.toString());
                if (this.updateCoursPersonne.executeUpdate() == 0) return false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }

        @Override
        public boolean deletePersonneCours(int idPersonne, int idCours, String date_debut, String date_fin) {
            try {        
                this.deleteCoursPersonne.setInt(1, idCours);
                this.deleteCoursPersonne.setInt(2,idPersonne);
                this.deleteCoursPersonne.setDate(3, Date.valueOf(date_debut));
                this.deleteCoursPersonne.setDate(4, Date.valueOf(date_fin));
                if (this.deleteCoursPersonne.executeUpdate() == 0) return false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        
        @Override
        public boolean addPersonneCours(int idPersonne, int idCours, String date_debut, String date_fin) {
            try {        
                this.insertCoursPersonne.setInt(1, idCours);
                this.insertCoursPersonne.setInt(2,idPersonne);
                this.insertCoursPersonne.setDate(3, Date.valueOf(date_debut));
                this.insertCoursPersonne.setDate(4, Date.valueOf(date_fin));
                if (this.insertCoursPersonne.executeUpdate() == 0) return false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
}
