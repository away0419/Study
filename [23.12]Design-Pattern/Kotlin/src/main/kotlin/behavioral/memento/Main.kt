package behavioral.memento

import java.util.Stack

fun main() {
    val stack = Stack<Memento>()
    val adventurer = Adventurer("전사", 10)

    println("초기 값: $adventurer")

    stack.push(adventurer.createMemento())
    adventurer.job = "법사"
    adventurer.level = 12

    println("변경 후: $adventurer")

    adventurer.setData(stack.pop())
    println("되돌리기: $adventurer")
}