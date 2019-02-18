package jdbc.dao;

import jdbc.model.Developer;
import jdbc.model.Project;
import java.util.Set;

public interface ProjectDao {
    Set<Project> findAllProjects();

    void updateProject(Project project);

    void deleteProjectById(Integer id);

    Project findProjectById(Integer id);

    void insertProject(Project project);

    void addDeveloperToProject(Project project,Developer developer);

    void deleteDeveloperFromProject(Project project, Developer developer);

}
