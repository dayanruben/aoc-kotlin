package aoc2022

import readInput

fun main() {
    val (year, day) = "2022" to "Day08"

    fun List<String>.parseTrees(): Map<Pair<Int, Int>, Char> {
        val trees = mutableMapOf<Pair<Int, Int>, Char>()
        this.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                trees[x to y] = c
            }
        }
        return trees
    }

    fun viewingDistance(range: IntProgression, height: Char, tree: (Int) -> Char) =
        when {
            range.isEmpty() -> 0
            else -> {
                val index = range.map { tree(it) }.indexOfFirst { it >= height }
                if (index < 0) range.count() else index + 1
            }
        }

    fun part1(input: List<String>): Int {
        val trees = input.parseTrees()
        val size = input.first().length
        return trees.entries.count { (pos, height) ->
            val (x, y) = pos
            val horizontal =
                (0 until x).all { trees[it to y]!! < height } ||
                        (x + 1 until size).all { trees[it to y]!! < height }
            val vertical =
                (0 until y).all { trees[x to it]!! < height } ||
                        (y + 1 until size).all { trees[x to it]!! < height }
            horizontal || vertical
        }
    }

    fun part2(input: List<String>): Int {
        val trees = input.parseTrees()
        val size = input.first().length
        return trees.maxOf { (pos, height) ->
            val (x, y) = pos
            val left = viewingDistance(x - 1 downTo 0, height) { trees[it to y]!! }
            val right = viewingDistance(x + 1 until size, height) { trees[it to y]!! }
            val up = viewingDistance(y - 1 downTo 0, height) { trees[x to it]!! }
            val down = viewingDistance(y + 1 until size, height) { trees[x to it]!! }
            left * right * up * down
        }
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 21)
    println(part1(input))

    check(part2(testInput) == 8)
    println(part2(input))
}