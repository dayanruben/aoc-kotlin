package aoc2023

import checkValue
import readInputText

fun main() {
    val (year, day) = "2023" to "Day05"

    fun fillMap(group: String): Map<Range, Long> {
        val map = mutableMapOf<Range, Long>()
        group.split("\n").drop(1).forEach { line ->
            val (destination, source, length) = line.split(" ").map { n -> n.toLong() }
            map[Range(source, source + length - 1)] = destination
        }
        return map
    }

    fun parseAlmanac(input: String): Almanac {
        val groups = input.split("\n\n")
        val almanac = Almanac(
                seeds = groups[0].split(" ").drop(1).map { it.toLong() },
                seedToSoil = fillMap(groups[1]),
                soilToFertilizer = fillMap(groups[2]),
                fertilizerToWater = fillMap(groups[3]),
                waterToLight = fillMap(groups[4]),
                lightToTemperature = fillMap(groups[5]),
                temperatureToHumidity = fillMap(groups[6]),
                humidityToLocation = fillMap(groups[7]),
        )
        return almanac
    }

    fun Map<Range, Long>.retrieve(ranges: List<Range>): List<Range> {
        return ranges.flatMap { (min, max) ->
            val rangeDest = mutableListOf<Range>()

            val inter = mutableListOf<Range>()
            for ((range, dest) in this) {
                val rmin = maxOf(min, range.min)
                val rmax = minOf(max, range.max)
                if (rmin <= rmax) {
                    inter.add(Range(rmin, rmax))
                    rangeDest.add(Range(rmin - range.min + dest, rmax - range.min + dest))
                }
            }
            inter.sortBy { it.min }
            var pivot = min
            for ((rmin, rmax) in inter) {
                if (rmin > pivot) {
                    rangeDest.add(Range(pivot, rmin - 1))
                }
                pivot = rmax + 1
            }
            if (pivot <= max) {
                rangeDest.add(Range(pivot, max))
            }

            rangeDest
        }
    }

    fun seedToLocationRange(almanac: Almanac, seedRange: List<Range>): List<Range> {
        val soil = almanac.seedToSoil.retrieve(seedRange)
        val fertilizer = almanac.soilToFertilizer.retrieve(soil)
        val water = almanac.fertilizerToWater.retrieve(fertilizer)
        val light = almanac.waterToLight.retrieve(water)
        val temperature = almanac.lightToTemperature.retrieve(light)
        val humidity = almanac.temperatureToHumidity.retrieve(temperature)
        return almanac.humidityToLocation.retrieve(humidity)
    }

    fun minToLocation(input: String, convertRanges: (List<Long>) -> List<Range>): Long {
        val almanac = parseAlmanac(input)
        val seedRanges = convertRanges(almanac.seeds)
        return seedToLocationRange(almanac, seedRanges).minOf { it.min }
    }

    fun part1(input: String) = minToLocation(input) { it.map { Range(it, it) } }

    fun part2(input: String) = minToLocation(input) { it.chunked(2).map { (min, len) -> Range(min, min + len) } }


    val testInput = readInputText(name = "${day}_test", year = year)
    val input = readInputText(name = day, year = year)

    checkValue(part1(testInput), 35)
    println(part1(input))

    checkValue(part2(testInput), 46)
    println(part2(input))
}

data class Almanac(
        val seeds: List<Long> = listOf(),
        val seedToSoil: Map<Range, Long> = mapOf(),
        val soilToFertilizer: Map<Range, Long> = mapOf(),
        val fertilizerToWater: Map<Range, Long> = mapOf(),
        val waterToLight: Map<Range, Long> = mapOf(),
        val lightToTemperature: Map<Range, Long> = mapOf(),
        val temperatureToHumidity: Map<Range, Long> = mapOf(),
        val humidityToLocation: Map<Range, Long> = mapOf(),
)

data class Range(val min: Long, val max: Long)
