package jdbc.service;

import jdbc.model.Developer;
import jdbc.model.Language;
import jdbc.model.Level;
import jdbc.model.Project;
import java.util.Set;

public interface DeveloperService {
    Double findSumOfSalariesOfAllDevelopers(Project project);

    Set<Developer> findAllDevelopersBySkill(Language language);

    Set<Developer> findAllDevelopersByLevel(Level level);

    Set<Developer> findAllDevelopers(Project project);

    Set<Developer> findAllDevelopers();

    void updateDeveloper(Developer developer);

    void deleteDeveloperById(Integer id);

    Developer findDeveloperById(Integer id);

    void insertDeveloper(Developer developer);
}
