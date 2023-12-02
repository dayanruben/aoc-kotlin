import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Gets a File from the given resource folder (year) and file name (day).
 */
private fun pathFrom(name: String, year: String) = Path("res/aoc$year/$name.txt")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, year: String) = pathFrom(name, year).readLines()

/**
 * Reads the entire content of the given input txt file as a String.
 */
fun readInputText(name: String, year: String) = pathFrom(name, year).readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


/**
 * Verifies whether the received value is equal to the expected value.
 * Print values when are differents.
 */
fun <T> checkValue(value: T, expectedValue: T) {
    check(value == expectedValue) {
        "value=$value expected=$expectedValue"
    }
}