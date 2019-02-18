package jdbc.service;

import jdbc.dao.DeveloperDao;
import jdbc.model.Developer;
import jdbc.model.Language;
import jdbc.model.Level;
import jdbc.model.Project;
import java.util.Set;

public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperDao developerDao;

    public DeveloperServiceImpl(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    public Double findSumOfSalariesOfAllDevelopers(Project project) {
        return developerDao.findSumOfSalariesOfAllDevelopers(project);
    }

    public Set<Developer> findAllDevelopersBySkill(Language language) { return developerDao.findAllDevelopersBySkill(language);}

    public Set<Developer> findAllDevelopersByLevel(Level level) { return developerDao.findAllDevelopersByLevel(level);}

    public Set<Developer> findAllDevelopers(Project project) {
        return developerDao.findAllDevelopers(project);
    }

    public Set<Developer> findAllDevelopers() {
        return developerDao.findAllDevelopers();
    }

    public void updateDeveloper(Developer developer) {
        developerDao.updateDeveloper(developer);
    }

    public void deleteDeveloperById(Integer id) {
        developerDao.deleteDeveloperById(id);
    }

    public Developer findDeveloperById(Integer id) {
        return developerDao.findDeveloperById(id);
    }

    public void insertDeveloper(Developer developer) {
        developerDao.insertDeveloper(developer);
    }

}
