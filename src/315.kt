package wip

/**
 * https://projecteuler.net/problem=315
 *
 * Direct calculation. Could have been more clever by just counting what is left on during transitions, as that's the
 * same thing. Whoops. Prime sieve is also super inefficient, but my quickly thrown together sieve produced an error so
 * this is still fast enough.
 */
fun main() {

  val fullCosts = mapOf(
      -1 to booleanArrayOf(false, false, false, false, false, false, false),
      0 to booleanArrayOf(true, true, true, false, true, true, true),
      1 to booleanArrayOf(false, false, false, false, false, true, true),
      2 to booleanArrayOf(false, true, true, true, true, true, false),
      3 to booleanArrayOf(false, false, true, true, true, true, true),
      4 to booleanArrayOf(true, false, false, true, false, true, true),
      5 to booleanArrayOf(true, false, true, true, true, false, true),
      6 to booleanArrayOf(true, true, true, true, true, false, true),
      7 to booleanArrayOf(true, false, true, false, false, true, true),
      8 to booleanArrayOf(true, true, true, true, true, true, true),
      9 to booleanArrayOf(true, false, true, true, true, true, true))
  val perDigitCosts = fullCosts.mapValues { (_, v) -> v.count { it } }

  val transitionCosts = mutableMapOf<Pair<Int, Int>, Int>()
  for ((i, cost) in fullCosts) {
    (-1..9).forEach { j ->
      val toCost = fullCosts[j]!!
      transitionCosts[i to j] = cost.zip(toCost).count { (a, b) -> a.xor(b) }
    }
  }

  val primeSieve = sieve(2*10_000_000)
  val primes = mutableListOf<Int>()
  for (i in 10_000_000 until primeSieve.size) {
    if (primeSieve[i]) primes.add(i)
  }

  val digitRootSequences = (1..64).map { x ->
    x to digitalSumSequence(x)
  }.toMap()

  val fullCostCache = digitRootSequences.map { (k, v) ->
    k to getSequenceCost(v, perDigitCosts)
  }.toMap()

  val transitionCostCache = digitRootSequences.map { (k, v) ->
    k to getTransitionSequenceCost(v, transitionCosts, perDigitCosts)
  }.toMap()

  val samsTotal = primes.map { p -> samsCost(p, perDigitCosts, fullCostCache).toLong() }.sum()
  val maxsTotal = primes.map { p -> maxsCost(p, transitionCosts, perDigitCosts, transitionCostCache).toLong() }.sum()
  println(samsTotal - maxsTotal)
}

fun samsCost(
    x: Int,
    onCosts: Map<Int, Int>,
    costCache: Map<Int, Int>
): Int = 2 * getSingleCost(x, onCosts) + costCache[digitalSum(x)]!!

fun getSequenceCost(
    xs: List<Int>,
    onCosts: Map<Int, Int>
 ): Int {
  return xs.map { 2 * getSingleCost(it, onCosts) }.sum()
}

fun getSingleCost(x: Int, perDigitCosts: Map<Int, Int>): Int {
  var x = x
  var s = 0
  while (x > 0) {
    s += perDigitCosts[x % 10]!!
    x /= 10
  }
  return s
}

fun maxsCost(
    x: Int,
    transitionCosts: Map<Pair<Int, Int>, Int>,
    perDigitCosts: Map<Int, Int>,
    costCache: Map<Int, Int>
): Int {
  val ds = digitalSum(x)
  return getSingleCost(x, perDigitCosts) +
      getTransitionCost(x, ds, transitionCosts, perDigitCosts) +
      costCache[ds]!!
}

fun getTransitionSequenceCost(
    xs: List<Int>,
    transitionCosts: Map<Pair<Int, Int>, Int>,
    perDigitCosts: Map<Int, Int>
): Int {
  var s = 0
  for (i in 0 until xs.size - 1) {
    s += getTransitionCost(xs[i], xs[i+1], transitionCosts, perDigitCosts)
  }
  return s + getSingleCost(xs.last(), perDigitCosts)
}

fun getTransitionCost(
    x: Int?,
    y: Int,
    transitionCosts: Map<Pair<Int, Int>, Int>,
    perDigitCosts: Map<Int, Int>): Int {
  if (x == null) {
    return getSingleCost(y, perDigitCosts)
  }
  var a = x
  var b = y
  var s = 0
  while (a > 0 || b > 0) {
    when {
      a > 0 && b > 0 -> s += transitionCosts[a%10 to b%10]!!
      a > 0          -> return s + getSingleCost(a, perDigitCosts)
      b > 0          -> return s + getSingleCost(b, perDigitCosts)
    }
    a /= 10
    b /= 10
  }
  return s
}

fun digitalSumSequence(x: Int): List<Int> {
  val roots = mutableListOf(x)
  var y = digitalSum(x)
  while (y != x) {
    roots.add(y)
    val z = digitalSum(y)
    if (z == y) break
    y = z
  }
  return roots
}

fun digitalSum(x: Int): Int {
  var x0 = x
  var s = 0
  while (x0 > 0) {
    s += x0 % 10
    x0 /= 10
  }
  return s
}

fun sieve(lim: Int): Array<Boolean> {
  val primes = Array(lim) { true }
  primes[0] = false
  primes[1] = false
  for (i in 2 until primes.size) {
    if (primes[i]) {
        for (j in i + i until primes.size step i) {
          primes[j] = false
        }
    }
  }
  return primes
}