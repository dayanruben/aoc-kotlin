package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day03"

    fun Char.toPriority() = if (this in 'a'..'z') this.code - 'a'.code + 1 else this.code - 'A'.code + 27

    fun part1(input: List<String>) =
        input.sumOf { rucksack ->
            val half = rucksack.length / 2
            val (first, second) = rucksack.chunked(half)
            first.find { it in second }?.toPriority() ?: 0
        }

    fun part2(input: List<String>) =
        input.chunked(3).sumOf { group ->
            val (r1, r2, r3) = group
            r1.find { it in r2 && it in r3 }?.toPriority() ?: 0
        }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 157)
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}