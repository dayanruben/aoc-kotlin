package aoc2021

import readInput

fun main() {
    val (year, day) = "2021" to "Day04"

    fun part1(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        return playBingo(boards, numbers)
    }

    fun part2(input: List<String>): Int {
        val (numbers, boards) = parseInput(input)
        return playBingo(boards, numbers, firstFound = false)
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

fun parseInput(input: List<String>): Pair<List<Int>, List<List<MutableList<Int>>>> {
    val numbers = input.first().split(",").map { it.toInt() }

    val lines = input.drop(1).filter { it.isNotEmpty() }
    val boards = lines.windowed(5, 5).map {
        it.map { row ->
            row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
        }.toMutableList()
    }.toMutableList()

    return numbers to boards
}

fun playBingo(
    boards: List<List<MutableList<Int>>>,
    numbers: List<Int>,
    firstFound: Boolean = true
): Int {
    val boardBingo = BooleanArray(boards.size)
    var bingoLeft = boards.size
    var hasZero = false

    numbers.forEach { number ->
        if (number == 0) hasZero = true
        boards.forEachIndexed { index, board ->
            if (boardBingo[index]) return@forEachIndexed

            board.forEach { row ->
                row.forEachIndexed { index, value ->
                    if (value == number) {
                        row[index] *= -1
                    }
                }
            }
            val (bingo, sum) = board.checkBoard(hasZero)
            if (bingo) {
                boardBingo[index] = bingo
                if (firstFound || --bingoLeft == 0) {
                    return sum * number
                }
            }
        }
    }

    return 0
}

fun List<List<Int>>.sumBoard(): Int {
    return sumOf { it.filter { it > 0 }.sum() }
}

fun List<List<Int>>.checkBoard(hasZero: Boolean): Pair<Boolean, Int> {
    for (row in indices) {
        val bingo = this[row].all { if (hasZero) it <= 0 else it < 0 }
        if (bingo) return Pair(true, sumBoard())
    }

    for (col in this[0].indices) {
        val bingo = all { if (hasZero) it[col] <= 0 else it[col] < 0 }
        if (bingo) return Pair(true, sumBoard())
    }

    return false to 0
}