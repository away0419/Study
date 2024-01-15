package behavioral.chainOfResponsibility

fun main() {
    val admin = Admin()
    val user = User()

    admin.setNextHandler(user)

    admin.process("Admin")

    println()
    admin.process("User")

    println()
    admin.process("")
}


