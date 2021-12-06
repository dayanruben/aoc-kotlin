package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day02"

    data class State(
        private val hasAim: Boolean = false,
        var horizontal: Int = 0,
        var depth: Int = 0,
        var aim: Int = 0
    ) {
        fun move(direction: String, x: Int) = if (hasAim) {
            when (direction) {
                "down" -> aim += x
                "up" -> aim -= x
                "forward" -> {
                    horizontal += x
                    depth += aim * x
                }
                else -> {}
            }
        } else {
            when (direction) {
                "forward" -> horizontal += x
                "down" -> depth += x
                "up" -> depth -= x
                else -> {}
            }
        }
    }

    fun calculate(input: List<String>, hasAim: Boolean): Int {
        val state = State(hasAim)

        input.forEach {
            val (direction, value) = it.split(" ")
            val x = value.toInt()
            state.move(direction, x)
        }

        return state.horizontal * state.depth
    }

    fun part1(input: List<String>) = calculate(input, hasAim = false)

    fun part2(input: List<String>) = calculate(input, hasAim = true)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 150)
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}