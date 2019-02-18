package jdbc.dao;

import jdbc.MyException;
import jdbc.model.Developer;
import jdbc.model.Language;
import jdbc.model.Level;
import jdbc.model.Project;
import jdbc.model.Skill;
import jdbc.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeveloperDaoImpl extends AbstractDao implements DeveloperDao {
    public DeveloperDaoImpl(Connection connection) {
        super(connection);
    }

    public Double findSumOfSalariesOfAllDevelopers(Project project) {
        final String findSumOfSalariesOfAllDevelopers = "SELECT sum(developer.salary) as sumDevSalary " +
                "FROM developer " +
                "INNER JOIN developer_project " +
                "ON developer.id = developer_project.developer_id " +
                "INNER JOIN project " +
                "ON project.id = developer_project.project_id " +
                "WHERE project.id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findSumOfSalariesOfAllDevelopers);
            preparedStatement.setInt(1,project.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("sumDevSalary");
            }

            return null;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public Set<Developer> findAllDevelopersBySkill(Language language) {
        final String findAllJavaDevelopers = "SELECT * FROM developer " +
                "INNER JOIN developer_skill " +
                "ON developer.id = developer_skill.developer_id " +
                "INNER JOIN skill " +
                "ON skill.id = developer_skill.skill_id " +
                "WHERE skill.language = \"" + String.valueOf(language) + "\"";

        return getDevelopers(findAllJavaDevelopers);
    }

    public Set<Developer> findAllDevelopersByLevel(Level level) {
        final String findAllMiddleDevelopers = "SELECT * FROM developer " +
                "INNER JOIN developer_skill " +
                "ON developer.id = developer_skill.developer_id " +
                "INNER JOIN skill " +
                "ON skill.id = developer_skill.skill_id " +
                "WHERE skill.level = \"" + String.valueOf(level) + "\"";

        return getDevelopers(findAllMiddleDevelopers);
    }

    public Set<Developer> findAllDevelopers(Project project) {
        final String findAllDevelopers = "SELECT * FROM developer " +
                "INNER JOIN developer_project " +
                "ON developer.id = developer_project.developer_id " +
                "INNER JOIN project " +
                "ON project.id = developer_project.project_id " +
                "WHERE project.id = " + project.getId();

        return getDevelopers(findAllDevelopers);
    }

    public Set<Developer> findAllDevelopers() {
        final String findAllDevelopers = "SELECT * FROM developer";

        return getDevelopers(findAllDevelopers);
    }

    public void updateDeveloper(Developer developer) {
        final String updateDeveloper = "UPDATE developer SET name = ?, age = ?, sex = ?, salary = ? WHERE id = ?";
        final String updateDeveloper_Project = "UPDATE developer_project SET project_id = ?  WHERE developer_id = ?";
        final String updateDeveloper_Skills = "UPDATE developer_skill SET skill_id = ? WHERE developer_id = ?";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(updateDeveloper);
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setString(3, developer.getSex());
            preparedStatement.setDouble(4, developer.getSalary());
            preparedStatement.setInt(5, developer.getId());
            preparedStatement.executeUpdate();

            Set<Project> projects = developer.getProjects();
            Set<Skill> skills = developer.getSkills();
            for (Project project : projects) {
                preparedStatement = connection.prepareStatement(updateDeveloper_Project);
                preparedStatement.setInt(1, project.getId());
                preparedStatement.setInt(2, developer.getId());
                preparedStatement.executeUpdate();
            }
            for (Skill skill : skills) {
                preparedStatement = connection.prepareStatement(updateDeveloper_Skills);
                preparedStatement.setInt(1, skill.getId());
                preparedStatement.setInt(2, developer.getId());
                preparedStatement.executeUpdate();
            }
            developer.setId(findMaxId(Table.DEVELOPER));
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public Developer findDeveloperById(Integer id) {
        final String findDeveloper = "SELECT * FROM developer WHERE id = " + id;

        return getDevelopers(findDeveloper).iterator().next();
    }

    public void deleteDeveloperById(Integer id) {
        final String deleteDeveloper = "DELETE FROM developer WHERE id = ?";
        final String deleteAllSkillsFromDeveloper_Skills = "DELETE FROM developer_skill WHERE developer_id = ?";
        final String deleteAllProjectsFromDeveloper_Project = "DELETE FROM developer_project WHERE developer_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteDeveloper);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(deleteAllSkillsFromDeveloper_Skills);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(deleteAllProjectsFromDeveloper_Project);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void insertDeveloper(Developer developer) {
        final String insertDeveloper = "INSERT INTO developer(name, age, sex, salary) values (?, ?, ?, ?)";
        final String insertIntoDeveloper_Project = "INSERT INTO developer_project(developer_id, project_id) values (?, ?)";
        final String insertIntoDeveloper_Skills = "INSERT INTO developer_skill(developer_id, skill_id) values (?, ?)";
        Integer maxId = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertDeveloper);
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setString(3, developer.getSex());
            preparedStatement.setDouble(4, developer.getSalary());
            preparedStatement.executeUpdate();

            maxId = findMaxId(Table.DEVELOPER);
            developer.setId(maxId);

            Set<Project> projects = developer.getProjects();
            Set<Skill> skills = developer.getSkills();
            for (Project project : projects) {
                preparedStatement = connection.prepareStatement(insertIntoDeveloper_Project);
                preparedStatement.setInt(1, maxId);
                preparedStatement.setInt(2, project.getId());
                preparedStatement.execute();
            }
            for (Skill skill : skills) {
                preparedStatement = connection.prepareStatement(insertIntoDeveloper_Skills);
                preparedStatement.setInt(1, maxId);
                preparedStatement.setInt(2, skill.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    private Set<Developer> getDevelopers(String sql) {
        Set<Developer> developers = new LinkedHashSet<>();
        final String findAllProjectsOfConcreteDeveloper = "SELECT * FROM project " +
                "INNER JOIN developer_project " +
                "ON project.id = developer_project.project_id " +
                "INNER JOIN developer " +
                "ON developer.id = developer_project.developer_id " +
                "WHERE developer.id = ?";
        final String findAllSkillsOfConcreteDeveloper = "SELECT * FROM skill " +
                "INNER JOIN developer_skill " +
                "ON skill.id = developer_skill.skill_id " +
                "INNER JOIN developer " +
                "ON developer.id = developer_skill.developer_id " +
                "WHERE developer.id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getInt("id"));
                developer.setAge(rs.getInt("age"));
                developer.setName(rs.getString("name"));
                developer.setSex(rs.getString("sex"));
                developer.setSalary(rs.getDouble("salary"));
                developers.add(developer);
            }
            for (Developer developer : developers) {
                preparedStatement = connection.prepareStatement(findAllProjectsOfConcreteDeveloper);
                preparedStatement.setInt(1,developer.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Project project = new Project();
                    project.setId(rs.getInt("id"));
                    project.setTypeOfProject(rs.getString("type"));
                    project.setCost(rs.getDouble("cost"));
                    project.setName(rs.getString("name"));
                    developer.addProject(project);
                }

                preparedStatement = connection.prepareStatement(findAllSkillsOfConcreteDeveloper);
                preparedStatement.setInt(1,developer.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Skill skill = new Skill();
                    skill.setId(rs.getInt("id"));
                    skill.setLevel(Level.valueOf(rs.getString("level")));
                    skill.setLanguage(Language.valueOf(rs.getString("language")));
                    developer.addSkill(skill);
                }
            }

            return developers;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }
}
