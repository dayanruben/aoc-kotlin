package aoc2023

import readInput
import checkValue

fun main() {
    val (year, day) = "2023" to "Day02"

    fun gameSets(game: String) =
            game.split(";").map {
                it.trim().split(",").map {
                    val (cubesStr, color) = it.trim().split(" ")
                    val cubes = cubesStr.toInt()
                    color to cubes
                }
            }

    fun gamePossibility(game: String): Int {
        val (gameId, gameInfo) = game.split(":")
        val (_, id) = gameId.split(" ")

        val sets = gameSets(gameInfo)
        val possible = sets.all { set ->
            set.all { (color, cubes) ->
                when (color) {
                    "red" -> cubes <= 12
                    "green" -> cubes <= 13
                    "blue" -> cubes <= 14
                    else -> false
                }
            }
        }

        return if (possible) id.toInt() else 0
    }

    fun gamePower(game: String): Int {
        val (_, gameInfo) = game.split(":")

        val sets = gameSets(gameInfo)

        val maxCubes = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        sets.forEach { set ->
            set.forEach { (color, cubes) ->
                maxCubes[color] = maxOf(maxCubes[color] ?: 0, cubes)
            }
        }

        return maxCubes.values.reduce { acc, i -> acc * i }
    }

    fun part1(input: List<String>) = input.sumOf { gamePossibility(it) }

    fun part2(input: List<String>) = input.sumOf { gamePower(it) }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 8)
    println(part1(input))

    checkValue(part2(testInput), 2286)
    println(part2(input))
}