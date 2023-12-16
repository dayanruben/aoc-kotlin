package aoc2023

import checkValue
import readInput

fun main() {
    val (year, day) = "2023" to "Day16"
    
    fun energizedTiles(layout: List<String>, startBeam: Beam): Int {
        val rows = layout.size
        val cols = layout.first().length
        val energized = Array(rows) { BooleanArray(cols) }
        val beams = mutableListOf(startBeam)
        val visited = mutableSetOf<Beam>()
        while (beams.isNotEmpty()) {
            val newBeams = mutableListOf<Beam>()
            for (beam in beams) {
                energized[beam.row][beam.col] = true
                visited.add(beam)
                val tile = layout[beam.row][beam.col]
                when (tile) {
                    '.' -> {
                        newBeams.add(beam.move())
                    }

                    '\\' -> {
                        val newDir = when (beam.dir) {
                            Direction.Down -> Direction.Right
                            Direction.Left -> Direction.Up
                            Direction.Right -> Direction.Down
                            Direction.Up -> Direction.Left
                        }
                        newBeams.add(beam.move(newDir))
                    }

                    '/' -> {
                        val newDir = when (beam.dir) {
                            Direction.Down -> Direction.Left
                            Direction.Left -> Direction.Down
                            Direction.Right -> Direction.Up
                            Direction.Up -> Direction.Right
                        }
                        newBeams.add(beam.move(newDir))
                    }

                    '|' -> {
                        if (beam.dir == Direction.Left || beam.dir == Direction.Right) {
                            newBeams.add(beam.move(Direction.Up))
                            newBeams.add(beam.move(Direction.Down))
                        } else {
                            newBeams.add(beam.move())
                        }
                    }

                    '-' -> {
                        if (beam.dir == Direction.Up || beam.dir == Direction.Down) {
                            newBeams.add(beam.move(Direction.Left))
                            newBeams.add(beam.move(Direction.Right))
                        } else {
                            newBeams.add(beam.move())
                        }
                    }
                }
            }
            beams.clear()
            beams.addAll(newBeams.filter { it.isValid(rows, cols) && it !in visited })
        }

        return energized.sumOf { row -> row.count { it } }
    }

    fun part1(input: List<String>) = energizedTiles(layout = input, startBeam = Beam(0, 0, Direction.Right))

    fun part2(input: List<String>): Int {
        val vertical = input.indices.flatMap { rowIndex ->
            listOf(
                energizedTiles(layout = input, startBeam = Beam(rowIndex, 0, Direction.Right)),
                energizedTiles(layout = input, startBeam = Beam(rowIndex, input.first().length - 1, Direction.Left))
            )
        }
        val horizontal = input.first().indices.flatMap { colIndex ->
            listOf(
                energizedTiles(layout = input, startBeam = Beam(0, colIndex, Direction.Down)),
                energizedTiles(layout = input, startBeam = Beam(input.size - 1, colIndex, Direction.Up))
            )
        }
        return maxOf(vertical.max(), horizontal.max())
    }

    val testInput = readInput(name = "${day}_test", year = year)
    val input = readInput(name = day, year = year)

    checkValue(part1(testInput), 46)
    println(part1(input))

    checkValue(part2(testInput), 51)
    println(part2(input))
}

data class Beam(val row: Int, val col: Int, val dir: Direction) {
    fun isValid(rows: Int, cols: Int) = row in 0..<rows && col in 0..<cols

    fun move(newDir: Direction = dir) = Beam(row + newDir.dr, col + newDir.dc, newDir)
}

sealed class Direction(val dr: Int, val dc: Int) {
    data object Right : Direction(0, 1)
    data object Left : Direction(0, -1)
    data object Up : Direction(-1, 0)
    data object Down : Direction(1, 0)
}