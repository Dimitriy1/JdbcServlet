package jdbc.dao;

import jdbc.model.Skill;
import java.util.Set;

public interface SkillDao {
    Set<Skill> findAllSkills();

    void updateSkill(Skill skill);

    void deleteSkills(Integer id);

    Skill findSkillById(Integer id);

    void insertSkill(Skill skill);
}
