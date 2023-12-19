package retry2

fun main() {

    val (N, K, C) = readln().split(" ").map { it.toInt() }
    val arr = readln().split(" ").map { it.toInt() }.toIntArray()
    val list = ArrayList<IntArray>()

    combination(0, 0, N, C, arr, list)
    val answer = binarySearch(N, K, list) + 1

    println(answer)
}

fun binarySearch(N: Int, K: Int, list: ArrayList<IntArray>): Long{
    var start = 1L
    var end = 1_000_000L * 1_000_000L

    while(start <= end){
        val mid = (start+end)/2
        var check = false

        for(arr in list) {
            var cnt = 0L

            arr.forEach {
                cnt += mid / it
            }

            if(cnt >= K ){
                check = true
                break
            }
        }

        if(check){
            end = mid -1
        }else{
            start = mid +1
        }
    }
    return end
}

fun combination(cur : Int, cnt : Int, N: Int, C: Int, arr: IntArray, list: ArrayList<IntArray>) {
    list.add(arr.copyOf())

    if(cnt == C){
        return
    }

    for (i in cur..<N){
        if(arr[i] == 1){
            continue
        }

        arr[i]--
        combination(i, cnt+1, N, C, arr, list)
        arr[i]++
    }
}