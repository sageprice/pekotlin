/**
 * https://projecteuler.net/problem=138
 *
 * Praise OEIS: https://oeis.org/A007805
 */
fun main() {
  val fs = fibonaccis(7 * 12)
  println((1..12).map { fs[6*it + 3] / 2 }.sum())
  // Experimental code used to generate the first few solutions.
  // After some algebra, we find that there is a solution whenever
  // 5*L^2 - 1 = k^2 for some integer k. So just iterate up from 0
  // and record whenever 5*L^2 - 1 is a square.
//  val squares = (0..10_000_000L).map { Pair(it*it, it) }
//  val sMap = squares.toMap()
//  val ls = mutableListOf<Long>()
//  for (i in 3 until squares.size step 2) {
//    val l = squares[i].second
//    val rad = 5L*squares[i].first - 1
//    if (sMap.containsKey(rad)) {
//      println("Possibility: $l")
//      val k = sMap[rad] ?: error("the heck? $l, $rad")
////      if (((2*k + 1) % 5) == 0L || ((2*k - 1) % 5) == 0L) {
//        ls.add(l)
//        println("Confirmed $l")
//        if (ls.size == 12) break
////      }
//    }
//  }
//  println(ls)
//  println(ls.sum())
}

fun fibonaccis(c: Int): List<Long> {
  val fs = mutableListOf(0L, 1, 1, 2, 3, 5, 8)
  while (fs.size < c+1) {
    fs.add(fs.last() + fs[fs.size - 2])
  }
  return fs
}