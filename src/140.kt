/**
 * https://projecteuler.net/problem=140
 *
 * So we have an infinite series, and we want to find solutions where rational input leads to positive integer output.
 * Let's call A_G(x) = y. Then we can re-write:
 *
 *   y = G_1*x + G_2*x^2 + G_3*x^3 + ...
 *
 * But wait, G_n = G_n-1 + G_n-2, so we can substitute from the third term on, then rearrange cleverly:
 *
 *   y = G_1*x + G_2*x^2 + (G_1 + G_2)*x^3 + (G_2 + G_3)*x^4 + ...
 *   y = G_1*x + G_2*x^2 + (G_1*x^3 + G_2*x^4 + G_3*x^5 + ...) + (G_2*x^3 + G_3*x^4 + G_4*x^5 + ...) + ...
 *
 * And recalling the definition of y, we can add a term and substitute to get:
 *
 *   y = G_1*x + G_2*x^2 + x^2*y + (x*y - G_1*x^2)
 *
 * And G_1 = 1, G_2 = 4, so this is just:
 *
 *   y = x + 4x^2 + yx^2 + yx - x^2 = (3+y)x^2 + (1+y)x ==> 0 = (3+y)x^2 + (1+y)x - y
 *
 * Cool, so now we have a direct relationship between x and y. We want y in N, and x in Q. If we assume y to be an int,
 * then we can just treat this as a quadratic equation and solve for y with the quadratic formula:
 *
 *     -(1+y) +- sqrt((1+y)^2 - 4*(3+y)(-y))
 *   -----------------------------------------
 *                 2*(3-y)
 *
 * But hey, all we care about is if x is rational: so we only care about whether that square root term is an integer.
 * So what we want is:
 *
 *   (1+y)^2 + 4y(3+y) = 5y^2 + 14y + 1 = n^2
 *
 * At this point we can use a Diophantine equation solver to get the base case and generators. I used this one:
 * https://www.alpertron.com.ar/QUAD.HTM
 *
 * Once we've generated a decent number of results, we take the 30 smallest where n,y > 0 (we only care about positive
 * results) and we're done.
 */
fun main() {
  var solutions = listOf(
      Solution(2, -7),
      Solution(0, -1),
      Solution(0, 1),
      Solution(-4, 5),
      Solution(-3, 2),
      Solution(-3, -2))

  var posSolutions = mutableListOf<Solution>()

  for (i in 1..15) {
    // There are a healthy number of repeats, so filter for distinct in each generation.
    solutions = (solutions.map { it.generateA() } + solutions.map { it.generateB() }).distinct()
    solutions.forEach { if (it.y > 0 && it.n > 0) posSolutions.add(it) }
    posSolutions = posSolutions.distinct() as MutableList<Solution>
    if (posSolutions.size > 30) break // Not sure if this is always safe to do, but it worked here.
  }
  println(posSolutions.sortedBy { it.y }.take(30).sumOf { it.y })
}

data class Solution(val y: Long, val n: Long)

fun Solution.generateA(): Solution {
  return Solution(-9*y - 4*n - 14, -20*y - 9*n - 28)
}
fun Solution.generateB(): Solution {
  return Solution(-9*y + 4*n - 14, 20*y - 9*n + 28)
}