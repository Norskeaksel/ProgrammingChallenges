import java.util.*
import kotlin.math.max

// typealias State = Pair<List<Int>, List<Int>>

typealias Answer = Pair<Int, List<Int>>

// val memoizationCache = mutableMapOf<State, Answer>()

fun bruteForce(
    people: List<Int>,
    crossedPeople: List<Int> = emptyList(),
    time: Int = 0,
    moves: List<Int> = emptyList(),
): Answer {
    /*val cacheKey = people to crossedPeople
    if (cacheKey in memoizationCache) {
        return memoizationCache[cacheKey]!!
    }*/

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
        val newCrossedPeople = crossedPeople.toMutableList()
        newCrossedPeople.add(p1)
        newCrossedPeople.add(p2)
        newCrossedPeople.sortDescending()
        val crossingTime = max(p1, p2)
        val remainingPeople = people.toMutableList()
        remainingPeople.remove(p1)
        remainingPeople.remove(p2)
        val newMoves = moves.toMutableList()
        newMoves.add(p1)
        newMoves.add(p2)
        if (remainingPeople.size > 0) {
            val personGoingBack = newCrossedPeople.last()
            newMoves.add(personGoingBack)
            newCrossedPeople.removeLast()
            val newTime = time + crossingTime + personGoingBack
            remainingPeople.add(personGoingBack)
            remainingPeople.sort()
            timesAndMoves.add(bruteForce(remainingPeople, newCrossedPeople, newTime, newMoves))
        } else {
            timesAndMoves.add(Pair(time + crossingTime, newMoves))
        }
    }

    val bestAnswer = timesAndMoves.minBy { it.first } // ?: Pair(time, moves) TODO: why is this not possible?
    // memoizationCache[cacheKey] = bestAnswer
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

fun bestMoves(fastest: Int, fast: Int, slow: Int, slowest: Int): Pair<Int, List<Int>> {
    val answer1 = option1(fastest, slow, slowest)
    val answer2 = option2(fastest, fast, slow, slowest)
    if (answer1.first <= answer2.first) {
        return answer1
    } else {
        return answer2
    }
}

fun option1(fastest: Int, slow: Int, slowest: Int): Pair<Int, List<Int>> {
    val moves = listOf(fastest, fastest, slow, fastest, fastest, slowest)
    val time = fastest + slow + fastest + slowest
    return Pair(time, moves)
}

fun option2(fastest: Int, fast: Int, slow: Int, slowest: Int): Pair<Int, List<Int>> {
    val moves = listOf(fastest, slow, slowest, fast, fastest, fast)
    val time = fastest + slowest + fast + fast
    return Pair(time, moves)
}

fun solve(people: List<Int>): Answer {
    if (people.size == 1) {
        return Pair(people[0], listOf(people[0]))
    }
    val fastest = people[0]
    val fast = people[1]
    val moves = mutableListOf(fastest, fast)
    var time = fast
    val remainingPeople = people.toMutableList()
    remainingPeople.remove(fastest)
    remainingPeople.remove(fast)
    while (remainingPeople.size >= 2) {
        val slow = remainingPeople[remainingPeople.size - 2]
        val slowest = remainingPeople[remainingPeople.size - 1]
        val answer = bestMoves(fastest, fast, slow, slowest)
        time += answer.first
        val newMoves = answer.second
        moves.addAll(newMoves)
        remainingPeople.remove(slow)
        remainingPeople.remove(slowest)
    }
    if (remainingPeople.size == 1) {
        val remainingDude = remainingPeople[0]
        time += fastest + remainingDude
        moves.addAll(listOf(fastest, fastest, remainingDude))
    }
    return Pair(time, moves)
}

fun main() {
    val sc = Scanner(System.`in`)
    var n = sc.nextInt()
    while (n-- > 0) {
        var p = sc.nextInt()
        val people = mutableListOf<Int>()
        while (p-- > 0) {
            people.add(sc.nextInt())
        }
        people.sort()
        System.err.println(bruteForce(people))
        val answer = solve(people)
        System.err.println(answer)
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
