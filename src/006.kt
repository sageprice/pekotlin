/** https://projecteuler.net/problem=6 direct calc */
fun main() {
  val s1 = (1..100).asSequence().map { it * it }.sum()
  val s2 = 101 * 50 // using Gaussian summation
  println(s2 * s2 - s1)
}