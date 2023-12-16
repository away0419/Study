package retry2

import java.util.HashMap
import java.util.PriorityQueue

fun main() {
    val N = readln().toInt()
    val listMap = HashMap<String, ArrayList<String>>()
    val levelMap = HashMap<String, Int>()
    val dq = ArrayDeque<String>()
    val sb = StringBuilder()
    var cnt = 0

    for (i in 0..<N) {
        val (a, b) = readln().split(" ")

        listMap[a] = listMap[a] ?: ArrayList<String>()
        listMap[a]?.add(b)
        levelMap[a] = levelMap[a] ?: 0
        levelMap[b] = (levelMap[b] ?: 0) + 1
    }

    levelMap.forEach {
        if (it.value == 0) {
            dq.add(it.key)
            levelMap[it.key] = -1
        }
    }

    while (dq.isNotEmpty()) {
        val dq2 = PriorityQueue<String>(dq)
        dq.clear()

        while (dq2.isNotEmpty()) {
            val str = dq2.poll()
            cnt++

            sb.append(str).append("\n")
            listMap[str]?.forEach {
                levelMap[it] = levelMap[it]!! - 1
                if (levelMap[it]!! == 0) {
                    dq.add(it)
                }
            }
        }
    }

    if(cnt != levelMap.size) println(-1) else println(sb)
}