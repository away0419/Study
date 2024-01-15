package behavioral.command

class Button (private var command: Command? = null){
    fun setCommand(command: Command){
        this.command = command
    }

    fun action(){
        this.command?.run()
    }
}