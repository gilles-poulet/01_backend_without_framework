package ue1104.iramps.be.Model.DAL.Interfaces;

import java.util.ArrayList;
import ue1104.iramps.be.Model.BL.Section;

public interface ISectionDAO {

    public ArrayList<Section> getSections();
    public int getIDSection(String nom);
    public Section getSection(int id);
    public boolean updateSection(int id, String nom);
    public boolean deleteSection(int id);
    public boolean addSection(String nom);
    public boolean close();
}
