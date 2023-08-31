import junit.framework.TestCase

class MainKtTest : TestCase() {
    fun testCheckForEqualResult() {
        repeat(10000) {
            val people = generateSortedRandomIntegers()
            println(people)
            val bruteForceResult = bruteForce(people)
            val result = solve(people)
            assertEquals(bruteForceResult.first, result.first)
        }
    }
}
