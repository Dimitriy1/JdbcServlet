package jdbc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Role {
    private Integer id;
    private TypeOfRole role;
    private Set<User> users = new LinkedHashSet<>();

    public Role() {

    }

    public Role(TypeOfRole role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfRole getRole() {
        return role;
    }

    public void setRole(TypeOfRole role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
