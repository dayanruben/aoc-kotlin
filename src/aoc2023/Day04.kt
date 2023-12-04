package aoc2023

import readInput
import checkValue

fun main() {
    val (year, day) = "2023" to "Day04"

    fun countMatches(cardLine: String): Int {
        val (_, cards) = cardLine.split(": ")
        val (win, mine) = cards.split("|").map { group ->
            group.trim().split("\\s+".toRegex()).map { num ->
                num.toInt()
            }
        }
        val winSet = win.toSet()
        return mine.count { it in winSet }
    }

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val matches = countMatches(line)
        Math.pow(2.0, (matches - 1).toDouble()).toInt()
    }

    fun part2(input: List<String>): Int {
        val cardCopies = IntArray(input.size) { 1 }
        for ((index, line) in input.withIndex()) {
            val matches = countMatches(line)
            if (matches > 0) {
                for (i in 1..matches) {
                    if (index + i >= cardCopies.size) break
                    cardCopies[index + i] += cardCopies[index]
                }
            }
        }
        return cardCopies.sum()
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 13)
    println(part1(input))

    checkValue(part2(testInput), 30)
    println(part2(input))
}