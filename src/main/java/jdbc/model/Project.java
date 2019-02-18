package jdbc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Project {
    private Integer id;
    private String name;
    private String TypeOfProject;
    private double cost;
    private Customer customer;
    private Set<Developer> developers = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfProject() {
        return TypeOfProject;
    }

    public void setTypeOfProject(String typeOfProject) {
        TypeOfProject = typeOfProject;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", TypeOfProject='" + TypeOfProject + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Double.compare(project.cost, cost) == 0 &&
                Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(TypeOfProject, project.TypeOfProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, TypeOfProject, cost);
    }
}
