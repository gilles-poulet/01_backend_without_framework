package ue1104.iramps.be.Model.DAL.Interfaces;

public interface IDAOFactory {
    public boolean close();
    public ISectionDAO createSectionDAO();
    public ICoursDAO createCoursDAO();
    public IPersonneDAO createPersonneDAO();
    public IStatusDAO createStatusDAO();
    public ICoursPersonneDAO createCoursPersonneDAO();
}
