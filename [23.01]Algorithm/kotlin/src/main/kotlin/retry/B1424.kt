package retry

fun main(){
    val N = readln().toInt()
    val L = readln().toInt()
    val C = readln().toInt()
    val length = L+1
    val size = C+1
    val max = size / length
    var answer = 10_000

    for (i in max downTo  1){
        if(i % 13 ==0){
            continue
        }

        val remainder = N % i
        var quotient = N / i

        if(remainder > 0) {
            if (remainder % 13 == 0 &&(quotient == 0 || remainder+1 == i)){
                quotient+=2
            }else{
                quotient++
            }
        }
        answer = quotient.coerceAtMost(answer)
    }

    println(answer)
}