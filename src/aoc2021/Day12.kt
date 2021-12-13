package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day12"

    fun buildMap(input: List<String>): Map<String, Set<String>> {
        val map = mutableMapOf<String, MutableSet<String>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            map.getOrPut(a) { mutableSetOf() }.add(b)
            map.getOrPut(b) { mutableSetOf() }.add(a)
        }
        return map
    }

    fun paths(
        map: Map<String, Set<String>>,
        origin: String,
        target: String,
        visited: MutableSet<String> = mutableSetOf(),
        extraVisit: Boolean = false
    ): Int = when {
        origin == target -> 1
        else -> {
            visited += origin
            val sum = map[origin]!!.filter {
                it !in visited || it == it.uppercase()
            }.sumOf {
                paths(map, it, target, visited.toMutableSet(), extraVisit)
            }

            val extraSum = if (extraVisit) {
                map[origin]!!.filter {
                    it in visited && it == it.lowercase() && it !in setOf("start", "end")
                }.sumOf {
                    paths(map, it, target, visited.toMutableSet(), false)
                }
            } else 0

            visited -= origin
            sum + extraSum
        }
    }

    fun part1(input: List<String>) =
        paths(buildMap(input), origin = "start", target = "end", extraVisit = false)

    fun part2(input: List<String>) =
        paths(buildMap(input), origin = "start", target = "end", extraVisit = true)

    val testInput1 = readInput(name = "${day}_test1", year = year)
    val testInput2 = readInput(name = "${day}_test2", year = year)
    val testInput3 = readInput(name = "${day}_test3", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput1) == 10)
    check(part1(testInput2) == 19)
    check(part1(testInput3) == 226)
    println(part1(input))

    check(part2(testInput1) == 36)
    check(part2(testInput2) == 103)
    check(part2(testInput3) == 3509)
    println(part2(input))
}