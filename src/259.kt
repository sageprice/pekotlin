import lib.Rational

private val ZERO = Rational(0)

/**
 * https://projecteuler.net/problem=259
 *
 * Sort of a divide and conquer/recursive solution, plus some DP since there is a lot of re-calculation possible.
 */
fun main() {
  val nums = "123456789"
  val reachableCache = mutableMapOf(Pair("", setOf(ZERO)))
  // Seeding the cache so we don't have to do anything clever at the base case.
  for (i in 1L..9) {
    reachableCache[i.toString()] = setOf(Rational(i))
  }
  println(calculateReachable(nums, reachableCache).filter { it > ZERO && it.d == 1L }.map { it.n }.sum())
}

fun calculateReachable(ns: String, cache: MutableMap<String, Set<Rational>>): Set<Rational> {
  if (cache.containsKey(ns)) return cache[ns]!!
  val reachables = mutableSetOf(Rational(ns.toLong()))
  for (i in 1 until ns.length) {
    reachables.addAll(
        combine(
            // Combine reachable numbers of the left component with reachable numbers of the right component.
            calculateReachable(ns.substring(0,i), cache),
            calculateReachable(ns.substring(i), cache)))
  }
  cache[ns] = reachables
  return reachables
}

fun combine(xs: Set<Rational>, ys: Set<Rational>): Set<Rational> {
  val combined = mutableSetOf<Rational>()
  xs.forEach { x ->
    ys.forEach { y ->
      combined.add(x+y)
      combined.add(x-y)
      combined.add(x*y)
      if (y != ZERO) {
        combined.add(x / y)
      }
    }
  }
  return combined
}