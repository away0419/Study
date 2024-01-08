package behavioral.command

class HeaterCommand: Command {
    override fun run() {
        println("히터 ON")
    }
}