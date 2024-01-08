package behavioral.command

class LampCommand: Command {
    override fun run() {
        println("램프 ON")
    }
}