package de.selucitus.aoc.y2023.e1

import java.io.File

fun main() {
    val file = File("src/de/selucitus/aoc/y2023/e1/input.txt")

    println("Solution for part 1: ${part1(file)}")
    println("Solution for part 2: ${part2(file)}")
}

/**
 * Solves part 1 of the exercise.
 * Reads a [file] line by line, concatenates the first and last digit of each line and sums the two-digit
 * numbers of each line
 *
 * @param file The file to evaluate
 * @return the sum of all two-digit numbers
 */
fun part1(file: File): Int = file.useLines { lines ->
    lines.sumOf {
        it.first { c -> c.isDigit() }.digitToInt() * 10 + it.last { c -> c.isDigit() }.digitToInt()
    }
}

/**
 * Solves part 2 of the exercise.
 * Like [part1] but digits can be written-out in English.
 *
 * @param file The file to evaluate
 * @return the sum of all two-digit numbers, including digits as written-out English words
 * @see part1 for only digits
 * @see numberMap keys for written-out English digits
 */
fun part2(file: File) = file.useLines { lines -> lines.sumOf { getTwoDigitNumber(it) } }

/**
 * Map of written-out English digits to the associated [Int] value
 */
val numberMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

/**
 * The valid digits separated by '|' to use it in the [numberRegex]
 */
val numberGroup =
    numberMap.keys.joinToString("|") + "|\\d"

/**
 * Regex to identify the first and last digit of a [String]. A digit is either a digit or written-out in English
 *
 * @see numberGroup for the valid digit representations
 */
val numberRegex = """.*?(?<first>$numberGroup).*(?<last>$numberGroup).*?|.*(?<only>$numberGroup).*""".toRegex()

/**
 * Converts a [digit] to an [Int]. [digit] must be either a digit or a key from [numberMap].
 * Single character digits are converted to [Int].
 *
 * @param digit The [String] to convert
 * @return the [digit] as [Int]
 * @throws [NumberFormatException] if [digit] is a single character [String] but not a digit
 */
fun getSanitizedDigit(digit: String) = if (digit.length == 1) digit.toInt() else numberMap.getOrDefault(digit, 0)

/**
 * Gets the first and last digit of a given [str], concatenates them and returns them as [Int].
 * If a [str] only has one valid digit, it is counted as first and last digit.
 *
 * @param str The [String] to get the first and last digit of
 * @return the first and last digit of a [str] concatenated as [Int] or 0 if no digit is found
 */
fun getTwoDigitNumber(str: String): Int {
    val regex = numberRegex.find(str) ?: return 0

    val only = regex.groups["only"]
    if (only != null) {
        return getSanitizedDigit(only.value) * 11
    }
    val first = regex.groups["first"]
    val last = regex.groups["last"]
    if (first != null) {
        return getSanitizedDigit(first.value) * 10 + getSanitizedDigit(last!!.value)
    }
    return 0
}
