import java.util.PriorityQueue
import kotlin.math.max

val memoizationCache = mutableMapOf<Pair<List<Int>, PriorityQueue<Int>>, Int>()

fun solve(
    people: List<Int>,
    crossedPeople: PriorityQueue<Int> = PriorityQueue<Int>(),
    time: Int = 0,
): Int {
    val cacheKey = people to crossedPeople
    if (cacheKey in memoizationCache) {
        return memoizationCache[cacheKey]!!
    }

    if (people.size == 1) return time + people[0]
    val allPairs = allPairs(people.size)
    System.err.println(allPairs)
    val times = mutableListOf<Int>()

    for (pair in allPairs) {
        val p1 = people[pair.first]
        val p2 = people[pair.second]
        val newCossedPeople = PriorityQueue(crossedPeople)
        newCossedPeople.add(p1)
        newCossedPeople.add(p2)
        val crossingTime = max(p1, p2)
        val remainingPeople = people.toMutableList()
        remainingPeople.remove(p1)
        remainingPeople.remove(p2)

        if (remainingPeople.size > 0) {
            val personGoingBack = newCossedPeople.poll()
            val newTime = time + crossingTime + personGoingBack
            remainingPeople.add(personGoingBack)
            times.add(solve(remainingPeople, newCossedPeople, newTime))
        } else {
            times.add(time + crossingTime)
        }
    }

    val minTime = times.minOrNull() ?: time
    memoizationCache[cacheKey] = minTime
    return minTime
}

fun allPairs(n: Int): MutableList<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until n - 1)
        for (j in i + 1 until n)
            pairs.add(Pair(i, j))
    return pairs
}

/*fun allPairs(n: Int) = (0 until n - 1).flatMap { i -> (i + 1 until n).map { j -> i to j } }*/
fun main(args: Array<String>) {
    var n = readln().toInt()
    readln()
    while (n-- > 0) {
        var p = readln().toInt()
        val people = mutableListOf<Int>()
        while (p-- > 0) {
            people.add(readln().toInt())
        }
        people.sort()
        println(solve(people))
    }
}
