/**
 * https://projecteuler.net/problem=127
 *
 * This works but it is way too slow. Some thoughts:
 * - since a + b = c, then gcd(a, b) = 1 => gcd(a, c) = 1 and gcd(b, c) = 1
 * - furthermore, it means rad(abc) = rad(a)rad(b)rad(c)
 */
fun main() {
  val limit = 120_000
  val rads = getRads(limit)
  var cSum = 0
  var count = 0
  for (c in 2..limit) {
    if (c % 1_000 == 0) println("At iteration $c")
    for (a in 1..c/2) { // a < b => a < c/2
      val b = c - a
      if (rads[a]*rads[b]*rads[c] >= c || gcd(a, b) != 1) continue
      count++
      cSum += c
    }
  }
  println("Found a total of $count triples")
  println(cSum)
}

fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)


// Using a similar approach to the Sieve of Eratosthenes.
fun getRads(n: Int): List<Long> {
  val factors = Array(n+1) { mutableSetOf<Long>()}
  factors[0].add(0)
  factors[1].add(1)
  for (i in 2 until factors.size) {
    if (factors[i].isEmpty()) {
      val iLong = i.toLong()
      for (j in i until factors.size step i) {
        factors[j].add(iLong)
      }
    }
  }
  return factors.map { it.reduce{ a, b -> a * b } }
}