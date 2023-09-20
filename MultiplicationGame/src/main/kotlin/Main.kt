import java.io.File
import java.io.PrintWriter
import java.util.*
import kotlin.math.sqrt

// val INPUT = File("src/main/kotlin/Input.txt").inputStream()

@JvmField
val INPUT = System.`in`

// val OUTPUT = File("src/main/kotlin/Output.txt").outputStream()
@JvmField
val OUTPUT = System.out

@JvmField
val _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readString() = _reader.readLine()!!

@JvmField
var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

@JvmField
val _writer = PrintWriter(OUTPUT, false)

fun readInt() = read().toInt()
fun readDouble() = read().toDouble()
fun readLong() = read().toLong()
fun readStrings(n: Int) = List(n) { read() }
fun readLines(n: Int) = List(n) { readString() }
fun readInts(n: Int) = List(n) { read().toInt() }
fun readIntArray(n: Int) = IntArray(n) { read().toInt() }
fun readDoubles(n: Int) = List(n) { read().toDouble() }
fun readDoubleArray(n: Int) = DoubleArray(n) { read().toDouble() }
fun readLongs(n: Int) = List(n) { read().toLong() }
fun readLongArray(n: Int) = LongArray(n) { read().toLong() }
fun readFileLines(fileName: String) = File(fileName).readLines()
fun debug(x: Any) = System.err.println("DEBUG: $x")

fun main() {
    _writer.solve(); _writer.flush()
}

fun PrintWriter.solve() {
    val n = readInt()
    val alice = "Alice"
    val bob = "Bob"
    repeat(n) {
        val (a, b) = readStrings(2)
        val first = if (b == bob) bob else alice
        val second = if (b == bob) alice else bob
        val target = a.toInt()
        val current = 1
        val factors = primeFactors(target)
        val remainingFactors = factors.toMutableMap()
        if (factors.values.sum() % 2 == 1) {
            while (current < target) {
                val mostCommonFactor = remainingFactors.maxByOrNull { it.value }!!.key
                remainingFactors[mostCommonFactor] = remainingFactors[mostCommonFactor]!! - 1
                if (remainingFactors.values.sum() == 0) {
                    println(first)
                    break
                }
                val leastCommonFactor = remainingFactors.minByOrNull { it.value }!!.key
                remainingFactors[leastCommonFactor] = remainingFactors[leastCommonFactor]!! - 1
                if (remainingFactors[leastCommonFactor] == -1) {
                    println("tie")
                    break
                }
            }
        } else {
            while (current < target) {
                val leastCommonFactor = remainingFactors.minByOrNull { it.value }!!.key
                remainingFactors[leastCommonFactor] = remainingFactors[leastCommonFactor]!! - 1
                if (remainingFactors[leastCommonFactor] == -1) {
                    println("tie")
                    break
                }
                val mostCommonFactor = remainingFactors.maxByOrNull { it.value }!!.key
                remainingFactors[mostCommonFactor] = remainingFactors[mostCommonFactor]!! - 1
                if (remainingFactors.values.sum() == 0) {
                    println(second)
                    break
                }
            }
        }
    }
}

fun primeFactors(a: Int): Map<Int, Int> {
    var n = a
    val factors = mutableMapOf<Int, Int>()
    var i = 0
    while (i <= sqrt(n.toDouble()) + 1) {
        if (i < 6) {
            while (n % 2 == 0) {
                factors[2] = factors.getOrDefault(2, 0) + 1
                n /= 2
            }
            while (n % 3 == 0) {
                factors[3] = factors.getOrDefault(3, 0) + 1
                n /= 3
            }
        } else {
            while (n % (i - 1) == 0) {
                factors[i - 1] = factors.getOrDefault(i - 1, 0) + 1
                n /= i - 1
            }
            while (n % (i + 1) == 0) {
                factors[i + 1] = factors.getOrDefault(i + 1, 0) + 1
                n /= i + 1
            }
        }
        i += 6
    }
    if (n > 1) {
        factors[n] = 1
    }
    return factors
}
