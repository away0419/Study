package singleton

fun main() {
    val singletone1 = CompanionObjectSingletone.createInstance()
    val singletone2 = CompanionObjectSingletone.createInstance()

    println(singletone1)
    println(singletone2)
}