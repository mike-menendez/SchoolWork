//Iterative dynamic programming problem.
//Traverse staircase with the lowest weight.
//Restricted to only choosing stair n or n + 1.

fun main() {
    readLine(); 
    val steps = readLine()!!.split(" ").map { x -> x.toInt() }.toIntArray()
    (2 until steps.size).forEach { x -> steps[x] = steps[x] + Math.min(steps[x - 1], steps[x - 2])}
    println(steps.last())
}
