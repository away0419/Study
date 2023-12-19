package retry2

fun main() {
    val N = readLine()!!.toInt()
    val lists = Array(N + 1) { ArrayList<Int>() }
    val linkCnt = IntArray(N + 1)

    repeat(N - 1) {
        val (a, b) = readLine()!!.split(" ").map { it.toInt() }
        lists[a].add(b)
        lists[b].add(a)
        linkCnt[a]++
        linkCnt[b]++
    }

    println(fn(N, linkCnt, lists))
}

fun fn(N: Int, linkCnt: IntArray, lists: Array<ArrayList<Int>>): String {
    val dq = search(N, linkCnt)
    val visits = BooleanArray(N + 1)
    val result = mutableListOf<Int>()

    while (dq.size > 2) {
        val dq2 = ArrayDeque(dq)
        dq.clear()

        while (dq2.isNotEmpty()) {
            val cur = dq2.removeLast()
            visits[cur] = true

            for (ints in lists[cur]) {
                if (visits[ints]) {
                    continue
                }

                if (--linkCnt[ints] == 1) {
                    dq.add(ints)
                }
            }
        }
    }

    for (i in 0 until N) {
        if (!visits[i]) {
            result.add(i)
        }
    }

    return result.joinToString(" ")
}

fun search(N: Int, linkCnt: IntArray): ArrayDeque<Int> {
    return ArrayDeque((0 until N).filter { linkCnt[it] == 1 })
}
