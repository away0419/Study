package retry2

fun main() {
    val a = readln()
    val b = readln()
    val chKey1 = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101)
    val chKey2 = listOf(103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239)
    val ht = MutableList(2000003) { mutableListOf<Pair<Int, Int>>() }
    var answer = 0

    for (i in a.indices) {
        var h1 = 1
        var h2 = 1
        var l = 1
        for (j in i..<a.length) {
            h1 = (h1 * chKey1[a[j]-'a']) % 2000003
            h2 = (h2 * chKey2[a[j]-'a']) % 2000003
            ht[h1].add(Pair(h2,l++))
        }
    }

    for (i in b.indices) {
        var h1 = 1
        var h2 = 1
        var l = 1
        for (j in i..<b.length) {
            h1 = (h1 * chKey1[b[j]-'a']) % 2000003
            h2 = (h2 * chKey2[b[j]-'a']) % 2000003

            if(Pair(h2,l) in ht[h1]) answer = answer.coerceAtLeast(l)
            l++
        }
    }

    println(answer)

}