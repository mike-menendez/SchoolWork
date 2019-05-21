//Iterative dynamic programming problem, leapfrog.
//find shortest path from inital lilypad to the end.

fun main() {
    readLine()
    val pads = readLine()!!.split(" ").map { x ->x.toInt() }.toList()
    val cache = IntArray(pads.size)
    cache[0] = 0
    (1 until pads.size).forEach { x ->
        cache[x] = Int.MAX_VALUE
        for(i in 0 until x){
            if(x <= i + pads[i] && cache[i] != Int.MAX_VALUE){
                cache[x] = Math.min(cache[x], cache[i] + 1)
                break
            }
        }
    }
    if(cache.last() == Int.MAX_VALUE) println(-1) else println(cache.last())
}
