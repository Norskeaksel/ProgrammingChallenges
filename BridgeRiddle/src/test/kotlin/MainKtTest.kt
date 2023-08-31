import junit.framework.TestCase

class MainKtTest : TestCase() {
    fun testCheckForEqualResult() {
        repeat(10_000) {
            val people = generateSortedRandomIntegers()
            println(people)
            val bruteForceResult = bruteForce(people)
            val result = solve(people)
            assertEquals(bruteForceResult.first, result.first)
        }
    }

    fun testJavaCode() {
        repeat(50_000) {
            val people = generateSortedRandomIntegers()
            println(people)
            val javaResult = Main.solve(people)
            val result = solve(people)
            assertEquals(javaResult.first, result.first)
        }
    }
}
