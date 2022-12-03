package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day03"

    fun priority(c: Char) = if (c in 'a'..'z') c.code - 'a'.code + 1 else c.code - 'A'.code + 27

    fun part1(input: List<String>) =
        input.sumOf { rucksack ->
            val first = rucksack.substring(0, rucksack.length / 2)
            val second = rucksack.substring(rucksack.length / 2, rucksack.length)
            val common = first.find { it in second } ?: ' '
            priority(common)
        }

    fun part2(input: List<String>) =
        input.windowed(3, 3).sumOf { group ->
            val (r1, r2, r3) = group
            val common = r1.find { it in r2 && it in r3 } ?: ' '
            priority(common)
        }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 157)
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}