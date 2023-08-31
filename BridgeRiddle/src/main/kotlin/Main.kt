import java.util.*
import kotlin.math.max

typealias State = Pair<List<Int>, List<Int>>

typealias Answer = Pair<Int, List<Int>>

// val memoizationCache = mutableMapOf<State, Answer>()

fun bruteForce(
    people: List<Int>,
    crossedPeople: List<Int> = emptyList(),
    time: Int = 0,
    moves: List<Int> = emptyList(),
): Answer {
    val cacheKey = people to crossedPeople
    /*if (cacheKey in memoizationCache) {
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

fun bestMoves(people: List<Int>, crossedPeople: List<Int>): Pair<Int, List<Int>> {
    val answer1 = option1(people, crossedPeople)
    val answer2 = option2(people, crossedPeople)
    if (answer1.first <= answer2.first) {
        return answer1
    } else {
        return answer2
    }
}

fun option1(people3: List<Int>, crossedPeople: List<Int>): Pair<Int, List<Int>> {
    assert(people3.size == 3)
    val p0 = people3[0]
    val p1 = people3[1]
    val crossingPeople = listOf(p0, p1)
    var time = max(p0, p1)
    val newCrossedPeople = crossedPeople.toMutableList()
    newCrossedPeople.addAll(crossingPeople)
    newCrossedPeople.sort()
    val personGoingBack = newCrossedPeople[0]
    time += personGoingBack
    time += max(people3[2], personGoingBack)
    val moves = listOf(p0, p1, personGoingBack)
    return Pair(time, moves)
}

fun option2(people3: List<Int>, crossedPeople: List<Int>): Pair<Int, List<Int>> {
    assert(people3.size == 3)
    val p1 = people3[1]
    val p2 = people3[2]
    var time = max(p1, p2)
    val crossingPeople = listOf(p1, p2)
    val newCrossedPeople = crossedPeople.toMutableList()
    newCrossedPeople.addAll(crossingPeople)
    newCrossedPeople.sort()
    val personGoingBack = newCrossedPeople[0]
    time += personGoingBack
    time += max(people3[0], personGoingBack)
    val moves = listOf(p1, p2, personGoingBack)
    return Pair(time, moves)
}

fun solve(people: List<Int>): Answer {
    val moves = mutableListOf<Int>()
    val crossedPeople = mutableListOf<Int>()
    val remainingPeople = people.toMutableList()
    var time = 0
    while (remainingPeople.size >= 3) {
        val subArray = remainingPeople.slice(0..2)
        val answer = bestMoves(subArray, crossedPeople)
        val newMoves = answer.second.slice(0..2)
        val crossingPeople = answer.second.slice(0..1)
        time += crossingPeople.max()
        crossedPeople.addAll(crossingPeople)
        for (person in crossingPeople) {
            remainingPeople.remove(person)
        }
        val returningPerson = answer.second[2]
        time += returningPerson
        crossedPeople.remove(returningPerson)
        remainingPeople.add(returningPerson)
        remainingPeople.sort()
        moves.addAll(newMoves)
    }
    moves.addAll(remainingPeople)
    time += remainingPeople.max()
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
