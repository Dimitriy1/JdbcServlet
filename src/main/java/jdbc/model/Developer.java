package jdbc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Developer {
    private Integer id;
    private String name;
    private int age;
    private String sex;
    private double salary;
    private Set<Project> projects = new LinkedHashSet<>();
    private Set<Skill>skills = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public double getSalary() {
        return salary;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return age == developer.age &&
                Double.compare(developer.salary, salary) == 0 &&
                Objects.equals(id, developer.id) &&
                Objects.equals(name, developer.name) &&
                Objects.equals(sex, developer.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, sex, salary);
    }
}
