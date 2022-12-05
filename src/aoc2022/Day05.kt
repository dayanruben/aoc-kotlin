package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day05"

    fun moveCrates(input: List<String>, onePerMove: Boolean): String {
        val stackLines = input.filter { '[' in it }
        val moveLines = input.filter { it.startsWith('m') }

        val stacks = Array((stackLines.maxOf { it.length } + 1) / 4) {
            ArrayDeque<Char>()
        }

        stackLines.forEach { line ->
            val crates = "$line ".chunked(4).map { it.trim() }
            crates.forEachIndexed { stack, crate ->
                if (crate.isNotEmpty()) {
                    stacks[stack].addFirst(crate[1])
                }
            }
        }

        moveLines.forEach { line ->
            val parts = line.split(" ")
            val amount = parts[1].toInt()
            val from = parts[3].toInt() - 1
            val to = parts[5].toInt() - 1

            if (onePerMove) {
                repeat(amount) {
                    val crate = stacks[from].removeLast()
                    stacks[to].addLast(crate)
                }
            } else {
                var order = ""
                repeat(amount) {
                    order += stacks[from].removeLast()
                }
                order.reversed().forEach { crate ->
                    stacks[to].addLast(crate)
                }
            }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    fun part1(input: List<String>) = moveCrates(input, onePerMove = true)

    fun part2(input: List<String>) = moveCrates(input, onePerMove = false)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == "CMZ")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}