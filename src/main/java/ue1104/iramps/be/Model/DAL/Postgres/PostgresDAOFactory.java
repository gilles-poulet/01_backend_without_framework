package ue1104.iramps.be.Model.DAL.Postgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import ue1104.iramps.be.Model.DAL.Interfaces.ICoursDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.ICoursPersonneDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.IDAOFactory;
import ue1104.iramps.be.Model.DAL.Interfaces.IPersonneDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.ISectionDAO;
import ue1104.iramps.be.Model.DAL.Interfaces.IStatusDAO;
import ue1104.iramps.be.Model.DAL.Postgres.Cours.CoursDAO;
import ue1104.iramps.be.Model.DAL.Postgres.Cours_Personne.CoursPersonneDAO;
import ue1104.iramps.be.Model.DAL.Postgres.Personne.PersonneDAO;
import ue1104.iramps.be.Model.DAL.Postgres.Sections.SectionDAO;
import ue1104.iramps.be.Model.DAL.Postgres.Status.StatusDAO;

import java.util.Properties;

public class PostgresDAOFactory implements IDAOFactory {
    Connection connection;
    final String SERVER="server";
    final String BDD="bdd";

    public PostgresDAOFactory() throws IOException, SQLException, ClassNotFoundException{

        // Récupération des propriétés
        Properties prop = new Properties();
        try{
            prop.load(getClass().getResourceAsStream("/db.properties"));
        } catch (IOException e){
            System.err.println("Les propriétés n'ont pas pu être chargées.");
            throw e;
        }

        // Création de la connexion
        Class.forName("org.postgresql.Driver");
        try{
            this.connection = DriverManager.getConnection(new StringBuilder("jdbc:postgresql://")
                                                                            .append(prop.getProperty(this.SERVER))
                                                                            .append("/")
                                                                            .append(prop.getProperty(this.BDD))
                                                                            .toString(),
                                                            prop);
        } catch (SQLException e) {
            System.err.println("La connexion vers la base de données à échouée.");
            throw e;
        }
    }

    @Override
    public boolean close() {
        if (this.connection != null){
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public ISectionDAO createSectionDAO() {
        return new SectionDAO(this.connection);
    }

    @Override
    public ICoursDAO createCoursDAO() {
        return new CoursDAO(connection);
    }

    @Override
    public IPersonneDAO createPersonneDAO() {
        return new PersonneDAO(connection);
    }

    @Override
    public IStatusDAO createStatusDAO() {
        return new StatusDAO(connection);
    }

    @Override
    public ICoursPersonneDAO createCoursPersonneDAO() {
        return new CoursPersonneDAO(connection);
    }

}
