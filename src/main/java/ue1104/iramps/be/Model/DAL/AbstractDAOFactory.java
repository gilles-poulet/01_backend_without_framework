package ue1104.iramps.be.Model.DAL;

import java.io.IOException;
import java.sql.SQLException;

import ue1104.iramps.be.Model.DAL.Interfaces.IDAOFactory;
import ue1104.iramps.be.Model.DAL.Postgres.PostgresDAOFactory;

public abstract class AbstractDAOFactory {
    public static IDAOFactory createFactory (EnumDAOType type) throws IOException, SQLException, ClassNotFoundException{
        IDAOFactory iDAOFactory = null;
        switch (type) {
            case POSTGRES:
                iDAOFactory = new PostgresDAOFactory();
                break;
        
            default:
                break;
        }
        return iDAOFactory;
    }
}
