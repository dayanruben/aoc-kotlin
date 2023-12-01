package aoc2023

import readInput

fun main() {
    val (year, day) = "2023" to "Day01"

    fun part1(input: List<String>) = input.map { line ->
        val digits = line.filter { it.isDigit() }
        "${digits.first()}${digits.last()}".toInt()
    }.sum()

    val digitsMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    fun part2(input: List<String>) = input.map { line ->
        val digits = buildString {
            for (i in line.indices) {
                if (line[i].isDigit()) {
                    append(line[i])
                }
                else {
                    val sub = line.substring(startIndex = i)
                    for ((word, num) in digitsMap) {
                        if (sub.startsWith(word)) {
                            append(num)
                            break
                        }
                    }
                }
            }
        }
        "${digits.first()}${digits.last()}".toInt()
    }.sum()

    val testInput1 = readInput(name = "${day}_p1_test", year = year)
    val testInput2 = readInput(name = "${day}_p2_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput1) == 142)
    println(part1(input))

    check(part2(testInput2) == 281)
    println(part2(input))
}