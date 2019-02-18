package jdbc;

import jdbc.dao.CustomerDaoImpl;
import jdbc.dao.DeveloperDaoImpl;
import jdbc.dao.ProjectDaoImpl;
import jdbc.dao.SkillDaoImpl;
import jdbc.model.Customer;
import jdbc.model.Developer;
import jdbc.model.Language;
import jdbc.model.Level;
import jdbc.model.Project;
import jdbc.model.Skill;
import jdbc.service.CustomerService;
import jdbc.service.CustomerServiceImpl;
import jdbc.service.DeveloperService;
import jdbc.service.DeveloperServiceImpl;
import jdbc.service.ProjectService;
import jdbc.service.ProjectServiceImpl;
import jdbc.service.SkillService;
import jdbc.service.SkillServiceImpl;

import java.sql.Connection;
import java.util.Set;

public class JdbcMain {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        CustomerService customerService = new CustomerServiceImpl(new CustomerDaoImpl(connection));
        SkillService skillService = new SkillServiceImpl(new SkillDaoImpl(connection));
        ProjectService projectService = new ProjectServiceImpl(new ProjectDaoImpl(connection));
        DeveloperService developerService = new DeveloperServiceImpl(new DeveloperDaoImpl(connection));

        Customer customer = new Customer();
        customer.setId(1);
        customer.setAge(20);
        customer.setName("Dmitriy");
        customer.setSex("male");
        customerService.insertCustomer(customer);

        Project project = new Project();
        project.setName("FirstProject");
        project.setCost(2000000.0);
        project.setCustomer(customer);
        projectService.insertProject(project);

        Skill skill = new Skill();
        skill.setId(1);
        skill.setLanguage(Language.JAVA);
        skill.setLevel(Level.JUNIOR);
        skillService.insertSkill(skill);

        Developer developer = new Developer();
        developer.setId(1);
        developer.setName("Ludmila");
        developer.setSalary(1000.0);
        developer.setAge(20);
        developer.addSkill(skill);
        developerService.insertDeveloper(developer);

        Developer developer1 = new Developer();
        developer1.setId(2);
        developer1.setName("Dima");
        developer1.setSalary(1000.0);
        developer1.setAge(24);
        developer1.addSkill(skill);
        developerService.insertDeveloper(developer1);

        Developer developer2 = new Developer();
        developer2.setId(3);
        developer2.setName("Dima");
        developer2.setSalary(1000000.0);
        developer2.setAge(25);
        developer2.addSkill(skill);
        developerService.insertDeveloper(developer2);

        project = projectService.findProjectById(project.getId());
        System.out.println(project);
        developer = developerService.findDeveloperById(developer.getId());
        System.out.println(developer);

        projectService.addDeveloperToProject(project,developer);
        projectService.addDeveloperToProject(project,developer1);
        projectService.addDeveloperToProject(project,developer2);
        Set<Developer>developers =  developerService.findAllDevelopersBySkill(Language.JAVA);

        for (Developer developer3 : developers) {
            System.out.println(developer3);
        }
        developerService.findAllDevelopersByLevel(Level.MIDDLE).forEach(System.out::println);
        System.out.println("sum = " + developerService.findSumOfSalariesOfAllDevelopers(project));
        projectService.findAllProjects().forEach(proj -> System.out.println("name : " +proj.getName()
                + " customer: " + proj.getCustomer() + " cost: " + proj.getCost()));

    }
}
