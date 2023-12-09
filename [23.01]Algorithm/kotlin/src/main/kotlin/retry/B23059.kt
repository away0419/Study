package retry

import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    var N = readln().toInt()
    val degreeMap = mutableMapOf<String, Int>()
    val linkMap = mutableMapOf<String, MutableList<String>>()

    while (N-- > 0) {
        val (a, b) = readln().split(" ")
        val list = linkMap[a] ?: mutableListOf<String>()

        degreeMap[a] = degreeMap[a] ?: 0
        degreeMap[b] = (degreeMap[b] ?: 0) + 1
        list.add(b)
        linkMap[a] = list
    }

    print(fn(degreeMap, linkMap))


}

fun fn(degreeMap: MutableMap<String, Int>, linkMap: MutableMap<String, MutableList<String>>): String {
    var cnt = degreeMap.size
    val dq = purchaseAbleList(degreeMap)
    val sb = StringBuilder("")

    while (dq.isNotEmpty()) {
        val dq2 = PriorityQueue<String>(dq)
        dq.clear()

        while (dq2.isNotEmpty()) {
            val str = dq2.poll()
            sb.append(str).append("\n")
            cnt--

            linkMap[str]?.forEach {
                degreeMap[it] = degreeMap[it]?.minus(1) ?: -1
                if (degreeMap[it] == 0) {
                    dq.add(it)
                }
            }
        }
    }
    return if (cnt == 0) sb.toString() else "-1"
}

fun purchaseAbleList(degreeMap: MutableMap<String, Int>): ArrayDeque<String> {
    val dq = ArrayDeque<String>()

    degreeMap.forEach { entry ->
        if (entry.value == 0) {
            dq.add(entry.key)
            degreeMap[entry.key]?.minus(1)
        }
    }

    return dq
}