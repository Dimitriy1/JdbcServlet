package jdbc.dao;

import jdbc.MyException;
import jdbc.model.Customer;
import jdbc.model.Developer;
import jdbc.model.Project;
import jdbc.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProjectDaoImpl extends AbstractDao implements ProjectDao {
    public ProjectDaoImpl(Connection connection) {
        super(connection);
    }

    public Set<Project> findAllProjects() {
        final String findAllProjects = "SELECT * FROM project";

        return getProjects(findAllProjects);
    }

    public void updateProject(Project project) {
        final String updateProject = "UPDATE project SET customer_id = ?, name = ?, type = ?, cost = ? WHERE id = ?";
        final String updateDeveloper_Project = "UPDATE developer_project SET developer_id = ? WHERE project_id = ?";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(updateProject);
            preparedStatement.setInt(1, project.getCustomer().getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getTypeOfProject());
            preparedStatement.setDouble(4, project.getCost());
            preparedStatement.setInt(5, project.getId());
            preparedStatement.executeUpdate();

            Set<Developer> developers = project.getDevelopers();
            for (Developer developer : developers) {
                preparedStatement = connection.prepareStatement(updateDeveloper_Project);
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.setInt(2, project.getId());
            }
            project.setId(findMaxId(Table.PROJECT));
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public Project findProjectById(Integer id) {
        final String findProjectById = "SELECT * FROM project where id = " + id;

        return getProjects(findProjectById).iterator().next();
    }

    public void deleteProjectById(Integer id) {
        final String deleteProjectById = "DELETE FROM project where id = " + id;
        final String deleteFromDeveloper_Project = "DELETE FROM developer_project where developer_id = " + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteProjectById);

            statement = connection.createStatement();
            statement.executeUpdate(deleteFromDeveloper_Project);
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void insertProject(Project project) {
        final String insertProject = "INSERT INTO project(customer_id, name, type, cost) values (?, ?, ?, ?)";
        final String insertIntoDeveloper_Project = "INSERT INTO developer_project (developer_id, project_id) values (?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertProject);
            preparedStatement.setInt(1, project.getCustomer().getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getTypeOfProject());
            preparedStatement.setDouble(4, project.getCost());
            preparedStatement.executeUpdate();

            Set<Developer> developers = project.getDevelopers();
            for (Developer developer : developers) {
                preparedStatement = connection.prepareStatement(insertIntoDeveloper_Project);
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.setInt(2, project.getId());
                preparedStatement.executeUpdate();
            }
            project.setId(findMaxId(Table.PROJECT));
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    private Set<Project> getProjects(String sql) {
        Set<Project> projects = new LinkedHashSet<>();
        final String findAllDevelopersOfConcreteProject = "SELECT * FROM developer " +
                "INNER JOIN developer_project " +
                "ON developer.id = developer_project.developer_id " +
                "INNER JOIN project " +
                "ON project.id = developer_project.project_id " +
                "WHERE project.id = ?";
        final String findCustomer = "SELECT * FROM customer " +
                "INNER JOIN project " +
                "ON customer.id = project.customer_id " +
                "WHERE project.id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setCost(rs.getDouble("cost"));
                project.setTypeOfProject(rs.getString("type"));
                projects.add(project);
            }
            for (Project project : projects) {
                preparedStatement = connection.prepareStatement(findAllDevelopersOfConcreteProject);
                preparedStatement.setInt(1,project.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Developer developer = new Developer();
                    developer.setId(rs.getInt("id"));
                    developer.setSex(rs.getString("sex"));
                    developer.setSalary(rs.getDouble("salary"));
                    developer.setName(rs.getString("name"));
                    developer.setAge(rs.getInt("age"));
                    project.addDeveloper(developer);
                }

                preparedStatement = connection.prepareStatement(findCustomer);
                preparedStatement.setInt(1,project.getId());
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setAge(rs.getInt("age"));
                    customer.setSex(rs.getString("sex"));
                    customer.setName(rs.getString("name"));
                    project.setCustomer(customer);
                }
            }

            return projects;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void addDeveloperToProject(Project project, Developer developer) {
        final String addDeveloperToProject = "INSERT INTO developer_project (developer_id, project_id) values (?, ?)";
        doRelationProjectWithDeveloper(addDeveloperToProject, project.getId(), developer.getId());
    }

    public void deleteDeveloperFromProject(Project project, Developer developer) {
        final String removeDeveloperToProject = "DELETE FROM developer_project WHERE developer_id = ? AND project_id = ?";
        doRelationProjectWithDeveloper(removeDeveloperToProject, project.getId(), developer.getId());
    }

    private void doRelationProjectWithDeveloper(String query, Integer projectId, Integer developerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, developerId);
            statement.setLong(2, projectId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
