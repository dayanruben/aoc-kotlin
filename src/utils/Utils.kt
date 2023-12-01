import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Gets a File from the given resource folder (year) and file name (day).
 */
private fun fileFrom(name: String, year: String) = File("res/aoc$year", "$name.txt")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, year: String) = fileFrom(name, year).readLines()

/**
 * Reads the entire content of the given input txt file as a String.
 */
fun readInputText(name: String, year: String) = fileFrom(name, year).readText()

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