package jdbc.dao;

import jdbc.MyException;
import jdbc.model.Developer;
import jdbc.model.Language;
import jdbc.model.Level;
import jdbc.model.Skill;
import jdbc.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SkillDaoImpl extends AbstractDao implements SkillDao {
    public SkillDaoImpl(Connection connection) {
        super(connection);
    }

    public Set<Skill> findAllSkills() {
        final String findAllSkills = "SELECT * FROM skill";

        return getSkills(findAllSkills);
    }

    public void updateSkill(Skill skill) {
        final String updateSkill = "UPDATE skill SET language = ?, level = ? WHERE id = ?";
        final String deleteDeveloper_Skill = "DELETE FROM developer_skill WHERE skill_id = ?";
        final String insertDeveloper_Skill = "INSERT INTO developer_skill (developer_id, skill_id) values (?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(updateSkill);
            preparedStatement.setString(1, String.valueOf(skill.getLanguage()));
            preparedStatement.setString(2, String.valueOf(skill.getLevel()));
            preparedStatement.setInt(3, skill.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(deleteDeveloper_Skill);
            preparedStatement.setInt(1, skill.getId());
            preparedStatement.executeUpdate();

            Set<Developer> developers = skill.getDevelopers();
            for (Developer developer : developers) {
                preparedStatement = connection.prepareStatement(insertDeveloper_Skill);
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.setInt(2, skill.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    public void deleteSkills(Integer id) {
        final String deleteSkills = "DELETE FROM skill WHERE id = ?";
        final String deleteFromDeveloper_Skills = "DELETE FROM developer_skill WHERE developer_skill.skill_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSkills);
            preparedStatement.setInt(1, id);
            preparedStatement = connection.prepareStatement(deleteFromDeveloper_Skills);
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    public Skill findSkillById(Integer id) {
        final String findSkillById = "SELECT * FROM skill where id = " + id;

        Iterator<Skill> skillIterator = getSkills(findSkillById).iterator();

        return skillIterator.hasNext() ? skillIterator.next() : null;
    }

    public void insertSkill(Skill skill) {
        final String insertSkill = "INSERT INTO skill (language, level) values (?, ?)";
        final String insertIntoDeveloper_Skills = "INSERT INTO developer_skill (developer_id, skill_id) values (?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertSkill);
            preparedStatement.setString(1, String.valueOf(skill.getLanguage()));
            preparedStatement.setString(2, String.valueOf(skill.getLevel()));
            preparedStatement.executeUpdate();

            Set<Developer> developers = skill.getDevelopers();
            for (Developer developer : developers) {
                preparedStatement = connection.prepareStatement(insertIntoDeveloper_Skills);
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.setInt(2, skill.getId());
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    private Set<Skill> getSkills(String sql) {
        Set<Skill> skills = new LinkedHashSet<>();
        final String findAllDevelopersOfConcreteSkill = "SELECT * FROM developer " +
                "INNER JOIN developer_skill " +
                "ON developer.id = developer_skill.developer_id " +
                "INNER JOIN skill " +
                "ON skill.id = developer_skill.skill_id " +
                "WHERE skill.id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("id"));
                skill.setLanguage(Language.valueOf(rs.getString("language")));
                skill.setLevel(Level.valueOf(rs.getString("level")));
                skills.add(skill);
            }
            for (Skill skill : skills) {
                preparedStatement = connection.prepareStatement(findAllDevelopersOfConcreteSkill);
                preparedStatement.setInt(1, skill.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Developer developer = new Developer();
                    developer.setId(rs.getInt("id"));
                    developer.setAge(rs.getInt("age"));
                    developer.setSalary(rs.getDouble("salary"));
                    developer.setSex(rs.getString("sex"));
                    developer.setName(rs.getString("name"));
                    skill.addDeveloper(developer);
                }
            }

            return skills;
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }
}
