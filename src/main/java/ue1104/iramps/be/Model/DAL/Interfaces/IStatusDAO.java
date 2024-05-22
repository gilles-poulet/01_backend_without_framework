package ue1104.iramps.be.Model.DAL.Interfaces;

import java.util.ArrayList;
import ue1104.iramps.be.Model.BL.Status;

public interface IStatusDAO {

    public ArrayList<Status> getAllStatus();
    public int getIDStatus(String nom);
    public Status getStatus(int id);
    public boolean updateStatus(int id, String nom);
    public boolean deleteStatus(int id);
    public boolean addStatus(String nom);
    public boolean close();
}
