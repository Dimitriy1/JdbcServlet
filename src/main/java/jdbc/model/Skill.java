package jdbc.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Skill {
    private Integer id;
    private Language language;
    private Level level;
    private Set<Developer> developers = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public void addDeveloper (Developer developer) {
        developers.add(developer);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", language=" + language +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) &&
                language == skill.language &&
                level == skill.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, level);
    }
}
