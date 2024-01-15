package behavioral.template

abstract class Adventurer {
    fun attack(){
        println("공격 전 준비 자세")
        action()
        println("공격 시작")
    }

    protected abstract fun action()
}