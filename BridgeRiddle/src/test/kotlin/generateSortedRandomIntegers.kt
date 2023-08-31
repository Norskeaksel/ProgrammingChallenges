import kotlin.random.Random
import kotlin.random.nextInt

fun generateSortedRandomIntegers(): List<Int> {
    val random = Random(System.currentTimeMillis())
    val listSize = random.nextInt(1, 6)
    val range = 1..10

    return (1..listSize).map { _ -> random.nextInt(range) }
        .sorted()
}
