package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day11"

    fun navigate(input: List<String>, maxStep: Int = 0): Int {
        val energy = input.flatMap { line -> line.map { c -> c - '0' } }.toMutableList()

        var flashes = 0
        var step = 0
        while (true) {
            step++
            val toFlash = ArrayDeque<Int>()
            energy.forEachIndexed { i, e ->
                energy[i] = e + 1
                if (energy[i] == 10) {
                    toFlash.addLast(i)
                }
            }

            val flashed = mutableSetOf<Int>()
            fun flash(index: Int) {
                flashed += index
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val x = index % 10 + dx
                        val y = index / 10 + dy
                        if (x in 0..9 && y in 0..9) {
                            val p = y * 10 + x
                            if (p !in flashed) {
                                energy[p] += 1
                                if (energy[p] == 10) {
                                    toFlash.addLast(p)
                                }
                            }
                        }
                    }
                }
            }

            while (toFlash.isNotEmpty()) {
                val index = toFlash.removeFirst()
                energy[index] = 0
                if (index !in flashed) {
                    flash(index)
                }
            }

            flashes += flashed.size
            if (maxStep > 0 && step == maxStep) {
                return flashes
            }
            if (maxStep == 0 && flashed.size == 100) {
                return step
            }
        }

        return 0
    }

    fun part1(input: List<String>) = navigate(input, maxStep = 100)

    fun part2(input: List<String>) = navigate(input)

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 1656)
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}