package de.selucitus.aoc.y2023.e2

import java.io.File

fun main() {
    val file = File("src/de/selucitus/aoc/y2023/e2/input.txt")

    println("Solution of part 1: ${part1(file)}")
    println("Solution of part 2: ${part2(file)}")
}

/**
 * Solves part 1 of the exercise.
 * Reads a [file] line by line, evaluates which game can be valid according to [gameRules].
 *
 * @param file The file to evaluate
 * @return the sum of all valid game indexes
 * @see gameRules for valid colors (key) and their maximum amount (value)
 */
fun part1(file: File): Int =
    file.useLines { lines ->
        lines.mapIndexed { index, game ->
            if (game
                    .substring(7 + (index + 1).toString().length)
                    .split("; ")
                    .all { isValidGame(it) }
            ) index + 1 else 0
        }.sum()
    }


/**
 * The maximum allowed amount [Int] of cubes per color [String]
 */
val gameRules = mapOf("red" to 12, "green" to 13, "blue" to 14)

/**
 * Determines if a given [game] is valid according to [gameRules].
 * A [game] follows the scheme: [Int] followed by space and a valid color (according to [gameRules]).
 * Multiple colors are separated by ", ".
 * E.g. "3 red, 4 blue"
 *
 * @param game The [String] in the required format
 * @return true if the game was valid
 */
fun isValidGame(game: String) = game.split(", ").all {
    val unit = it.split(' ')
    gameRules[unit[1]]!! >= unit[0].toInt()
}

/**
 * Solves part 2 of the exercise.
 * Gets the minimum amount of cubes per color and game and multiplies them ("power"). Return the sum of all products.
 *
 * @param file The file to evaluate
 * @return the sum of all game "powers"
 */
fun part2(file: File): Int = file.useLines { lines ->
    lines.mapIndexed { index, game ->
        game
            .substring(7 + (index + 1).toString().length).split("; ", ", ")
            .groupBy { it.split(' ')[1] }
            .map { it.value.maxOf { tuple -> tuple.split(' ')[0].toInt() } } // TODO unnecessary double split
            .reduce(Int::times)
    }.sum()
}
