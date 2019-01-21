import kotlin.math.sqrt

/** https://projecteuler.net/problem=7 brute force */
fun main(args: Array<String>) {
  var pCount = 1
  var p = 1

  while (pCount < 10001) {
    p += 2
    if (!(2..(sqrt(p.toDouble()).toInt())).asSequence().any { p % it == 0 })
      pCount++
  }
  println(p)
}