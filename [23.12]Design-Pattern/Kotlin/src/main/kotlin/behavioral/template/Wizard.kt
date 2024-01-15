package behavioral.template

class Wizard: Adventurer() {
    override fun action() {
        println("마나를 모은다.")
    }
}