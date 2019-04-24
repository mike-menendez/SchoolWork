import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

internal class Route(val from: String, val to: String, val weight: Int)

object Main {

    private fun parseRoute(s: String): Route {
        val parsed = ArrayList<String>()
        var i = 0
        var temp = ""
        while (i <= s.length) {
            if (i == s.length || s[i] == ',') {
                parsed.add(temp)
                temp = ""
                i++
            } else {
                temp += s[i]
            }
            i++
        }
        return Route(parsed[0], parsed[1],
                Integer.parseInt(parsed[2]))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)

        var num_citizens = Integer.parseInt(sc.nextLine().trim { it <= ' ' })
        val citizens = ArrayList<String>()
        while (num_citizens > 0) {
            val s = sc.nextLine().trim { it <= ' ' }
            citizens.add(s)
            num_citizens--
        }

        var num_services = Integer.parseInt(sc.nextLine().trim { it <= ' ' })
        val services = ArrayList<String>()
        while (num_services > 0) {
            val s = sc.nextLine().trim { it <= ' ' }
            services.add(s)
            num_services--
        }

        var num_routes = Integer.parseInt(sc.nextLine().trim { it <= ' ' })
        val routes = ArrayList<Route>()
        while (num_routes > 0) {
            val s = sc.nextLine().trim { it <= ' ' }
            routes.add(parseRoute(s))
            num_routes--
        }
        mayweather_Help_Me_Please_I_Need_An_Adult_And_Dont_Know_What_I_Am_Doing(routes, citizens, services)
    }

    private fun mayweather_Help_Me_Please_I_Need_An_Adult_And_Dont_Know_What_I_Am_Doing(routes: ArrayList<Route>, citizens: ArrayList<String>, services: ArrayList<String>) {
        val distanceMap = arrayListOf<ArrayList<Int>>()

        val locMap = HashMap<String, HashMap<String, Int>>()

        val axis = HashSet<String>(citizens + services).toList()

        routes.forEach { x ->
            run {
                if (locMap.containsKey(x.from)) {
                    locMap[x.from]?.put(x.to, x.weight)
                } else {
                    locMap[x.from] = HashMap()
                    locMap[x.from]!!.put(x.to, x.weight)
                }
            }
        }

        (0 until axis.size).forEach { i ->
            distanceMap.add(ArrayList())
            (0 until axis.size).forEach { _ ->
                distanceMap[i].add(Int.MAX_VALUE)
            }
        }

        (0 until axis.size).forEach { i ->
            (0 until axis.size).forEach { j ->
                if (locMap.containsKey(axis[i])) {
                    if (locMap[axis[i]]!!.containsKey(axis[j])) {
                        if (distanceMap[i][j] > locMap[axis[i]]!![axis[j]]!!) {
                            distanceMap[i][j] = locMap[axis[i]]!![axis[j]]!!
                            distanceMap[j][i] = locMap[axis[i]]!![axis[j]]!!
                        }
                    }
                }
            }
        }

        (0 until axis.size).forEach { i ->
            (0 until axis.size).forEach { j ->
                (0 until axis.size).forEach { k ->
                    if (distanceMap[k][i] != Int.MAX_VALUE && distanceMap[i][j] != Int.MAX_VALUE && distanceMap[k][i] + distanceMap[i][j] < distanceMap[k][j]) {
                        distanceMap[k][j] = distanceMap[k][i] + distanceMap[i][j]
                    }
                }
            }
        }

        services.sort()
        services.forEach { x -> print("$x ") }
        println()
        citizens.sort()
        citizens.forEach { x ->
            run {
                services.forEach { y -> print(distanceMap[axis.indexOf(y)][axis.indexOf(x)].toString() + " ") }
            }
            println(x)
        }
    }
}
