package jdbc.service;

import jdbc.dao.ProjectDao;
import jdbc.model.Developer;
import jdbc.model.Project;
import java.util.Set;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public Set<Project> findAllProjects() {
        return projectDao.findAllProjects();
    }

    public void updateProject(Project project) {
        projectDao.updateProject(project);
    }

    public void deleteProjectById(Integer id) {
        projectDao.deleteProjectById(id);
    }

    public Project findProjectById(Integer id) {
        return projectDao.findProjectById(id);
    }

    public void insertProject(Project project){
        projectDao.insertProject(project);
    }

    public void addDeveloperToProject(Project project, Developer developer) {
        projectDao.addDeveloperToProject(project, developer);
    }

    public void deleteDeveloperFromProject(Project project, Developer developer) {
        projectDao.deleteDeveloperFromProject(project, developer);
    }
}
