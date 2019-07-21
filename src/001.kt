/** Solution to Project Euler problem 1 in Kotlin. */
fun main() {
  val limit = 999
  // Brute forcey
  println((1..limit)
      .filter { (it % 3 == 0).or(it % 5 == 0) }
      .sum())

  // Kinda smart Gaussian sum.
  println(gaussSum(limit, 3) + gaussSum(limit, 5) - gaussSum(limit, 15))
}

/** [sum(x) : b | x and x <= m]. Uses Gaussian sum approach. */
fun gaussSum(m: Int, b: Int): Int {
  val c = m / b
  return b * (1 + c) * c / 2
}