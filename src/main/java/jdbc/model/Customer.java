package jdbc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Customer {
    private Integer id;
    private String name;
    private int age;
    private String sex;
    private Set<Project> projects = new LinkedHashSet<>();

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

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject (Project project) {
        projects.add(project);
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age &&
                Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(sex, customer.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, sex);
    }
}
