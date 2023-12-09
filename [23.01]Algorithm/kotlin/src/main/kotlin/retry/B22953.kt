package retry

fun main(args: Array<String>) {
    val (N, K, C) = readln().split(" ").map { it.toInt() }
    val arr: IntArray = readln().split(" ").map { it.toInt() }.toIntArray()
    val INF: Long = 1_000_000L * 1_000_000L
    val list: MutableList<IntArray> = mutableListOf<IntArray>()

    combination(0, 0, N, C, arr, list)

    val answer = binarySearch(N, K ,C ,INF, list)

    println(answer)
}

fun binarySearch(N: Int, K: Int, C: Int, INF: Long, list: MutableList<IntArray>) : Long{
    var start = 0L
    var end = INF

    while (start <= end) {
        val mid = (start + end) / 2
        var cookCnt = 0L

        for(ints in list){
            var max = 0L

            for(i in 0..<N){
                max += mid / ints[i]
            }

            cookCnt = max.coerceAtLeast(cookCnt)
        }

        if(cookCnt < K){
            start = mid +1
        } else {
            end = mid -1
        }
    }

    return end+1
}

fun combination(cur: Int, cnt: Int, N: Int, C: Int, arr: IntArray, list: MutableList<IntArray>) {
    if (C == cnt) {
        list.add(arr.copyOf())
        return;
    }

    for (i in cur..<N) {
        if (arr[i] == 1) {
            combination(i, cnt + 1, N, C, arr, list)
            continue
        }
        arr[i]--
        combination(i, cnt + 1, N, C, arr, list)
        arr[i]++
    }
}