import junit.framework.TestCase
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class MainKtTest : TestCase() {
    fun testMain() {
        main()
        val myAnswer = File("src/main/kotlin/Output.txt").readText()
        val answer = File("src/test/kotlin/TestOutput.txt").readText()
        assertEquals(answer, myAnswer)
    }
}