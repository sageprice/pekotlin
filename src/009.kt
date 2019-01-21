/** https://projecteuler.net/problem=9 */
fun main(args: Array<String>) {
  for (a in 150..333) { // a is short leg, at most 1/3 total perimeter
    for (b in (a + 1)..(1000 - 2 * a)) { // a < b < c
      val c = 1000 - a - b // duh
      if (a * a + b * b == c * c) {
        println(a * b * c)
        return
      }
    }
  }
}