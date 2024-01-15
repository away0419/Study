package behavioral.strategy;

public class Adventurer {
    private Skill skill;

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void useSkill(){
        skill.active();
    }
}
