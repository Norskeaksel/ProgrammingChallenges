import kotlin.math.max

typealias State = Pair<List<Int>, List<Int>>

typealias Answer = Pair<Int, List<Int>>

val memoizationCache = mutableMapOf<State, Answer>()

fun solve(
    people: List<Int>,
    crossedPeople: List<Int> = emptyList(),
    time: Int = 0,
    moves: List<Int> = emptyList(),
): Answer {
    val cacheKey = people to crossedPeople
    if (cacheKey in memoizationCache) {
        return memoizationCache[cacheKey]!!
    }

    if (people.size == 1) {
        val newMoves = moves.toMutableList()
        newMoves.add(people[0])
        return Pair(time + people[0], newMoves)
    }
    val allPairs = allPairs(people.size)
    /*allPairs.forEach {
        System.err.print("(${people[it.first]} ${people[it.second]}), ")
    }
    System.err.println()*/
    val timesAndMoves = mutableListOf<Answer>()

    for (pair in allPairs) {
        val p1 = people[pair.first]
        val p2 = people[pair.second]
        val newCossedPeople = crossedPeople.toMutableList()
        newCossedPeople.add(p1)
        newCossedPeople.add(p2)
        newCossedPeople.sortDescending()
        val crossingTime = max(p1, p2)
        val remainingPeople = people.toMutableList()
        remainingPeople.remove(p1)
        remainingPeople.remove(p2)
        val newMoves = moves.toMutableList()
        newMoves.add(p1)
        newMoves.add(p2)
        if (remainingPeople.size > 0) {
            val personGoingBack = newCossedPeople.last()
            newMoves.add(personGoingBack)
            newCossedPeople.removeLast()
            val newTime = time + crossingTime + personGoingBack
            remainingPeople.add(personGoingBack)
            remainingPeople.sort()
            timesAndMoves.add(solve(remainingPeople, newCossedPeople, newTime, newMoves))
        } else {
            timesAndMoves.add(Pair(time + crossingTime, newMoves))
        }
    }

    val bestAnswer = timesAndMoves.minBy { it.first } // ?: Pair(time, moves) TODO: why is this not possible?
    memoizationCache[cacheKey] = bestAnswer
    return bestAnswer
}

fun allPairs(n: Int): MutableList<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until n - 1)
        for (j in i + 1 until n)
            pairs.add(Pair(i, j))
    return pairs
}

/*fun allPairs(n: Int) = (0 until n - 1).flatMap { i -> (i + 1 until n).map { j -> i to j } }*/
fun main() {
    var n = readln().toInt()
    while (n-- > 0) {
        readln()
        var p = readln().toInt()
        val people = mutableListOf<Int>()
        while (p-- > 0) {
            people.add(readln().toInt())
        }
        people.sort()
        val answer = solve(people)
        println(answer.first)
        var needExtraNewline = true
        for (i in 0 until answer.second.size) {
            needExtraNewline = false
            print("${answer.second[i]} ")
            if (i % 3 != 0) {
                println()
            } else {
                needExtraNewline = true
            }
        }
        if (needExtraNewline) {
            println("\n")
        } else {
            println()
        }
    }
}
