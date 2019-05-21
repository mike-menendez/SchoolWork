//Iterative dynamic programming solution for calculating number of combinations that can make the goal outcome
//The coin values are provided (such as penny, nickel, dime, etc.)

fun main() {
    val goal = readLine()!!.toInt(); readLine()
    val coins = readLine()!!.split(" ").map { x -> x.toInt() }.sorted()
    val combos = (0..goal).map { _ -> 0 }.toIntArray(); combos[0] = 1
    coins.forEach { x -> (0 until combos.size).forEach { y -> if(y >= x) combos[y] = combos[y] + combos[y-x] }}
    print(combos[goal])
}
