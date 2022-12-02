package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day02"

    fun calculateScore(input: List<String>, withPlay: Boolean): Int {
        val lose = mapOf(1 to 0, 2 to 1, 0 to 2)
        val won = mapOf(0 to 1, 1 to 2, 2 to 0)

        return input.sumOf { round ->
            val (op, result) = round.split(" ").map { it[0].code }
            val opCode = op - 'A'.code
            val meCode = result - 'X'.code

            val (resultScore, playScore) = if (withPlay) {
                when {
                    opCode == meCode -> 3
                    won[opCode]!! == meCode -> 6
                    else -> 0
                } to meCode + 1
            } else {
                3 * meCode to when (meCode) {
                    0 -> lose[opCode]!!
                    1 -> opCode
                    else -> won[opCode]!!
                } + 1
            }

            resultScore + playScore
        }
    }

    fun part1(input: List<String>) =
        calculateScore(input, withPlay = true)

    fun part2(input: List<String>) =
        calculateScore(input, withPlay = false)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 15)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}