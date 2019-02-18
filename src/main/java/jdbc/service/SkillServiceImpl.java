package jdbc.service;

import jdbc.dao.SkillDao;
import jdbc.model.Skill;
import java.util.Set;

public class SkillServiceImpl implements SkillService {
    private final SkillDao skillDao;

    public SkillServiceImpl(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    public Set<Skill> findAllSkills() {
        return skillDao.findAllSkills();
    }

    public void updateSkill(Skill skill) {
        skillDao.updateSkill(skill);
    }

    public void deleteSkills(Integer id) {
        skillDao.deleteSkills(id);
    }

    public Skill findSkillById(Integer id) {
        return skillDao.findSkillById(id);
    }

    public void insertSkill(Skill skill) {
        skillDao.insertSkill(skill);
    }
}
