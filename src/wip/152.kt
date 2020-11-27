package wip

import kotlin.math.abs

val TARGET = .5
val ERROR = .0000000001

/**
 * https://projecteuler.net/problem=152
 */
fun main() {
  val end = 35
  val remainingSums = mutableMapOf<Int, Double>()
  for (i in 2..end) {
    remainingSums[i] = (i..end).map { 1.0 / (it*it) }.sum()
    println("$i:\t${remainingSums[i]}")
  }
  println("Found a total of ${recursivelySum(2, 0.0, remainingSums)}")
}

fun recursivelySum(x: Int, s: Double, sumsOfRemaining: Map<Int, Double>): Int {
  if (sumsOfRemaining[x] == null) return 0
  // Exit early if sum of remaining is less than target.
  if (s + (sumsOfRemaining[x] ?: error("INVALID INDEX $x")) < TARGET) return 0
  val sansCurrent = recursivelySum(x+1, s, sumsOfRemaining)
  val newS = s + 1.0/(x*x)
  val withCurrent =
      when {
        abs(newS - TARGET) < ERROR -> 1
        newS < TARGET              -> recursivelySum(x+1, newS, sumsOfRemaining)
        else                  -> 0
      }
  return sansCurrent + withCurrent
}