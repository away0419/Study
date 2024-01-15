package behavioral.strategy

class Adventurer(var skill: Skill) {
    fun useSkill(){
        skill.active()
    }
}