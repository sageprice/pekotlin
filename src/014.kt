/**
 * https://projecteuler.net/problem=14
 *
 * Direct calculation with minor memoization.
 */
fun main(args: Array<String>) {

  val seqs: Array<Int> = Array(1_000_000) { 0 }

  for (i in 1L until seqs.size) {
    if (seqs[i.toInt()] == 0) {
      val x = collatz(i, seqs)
      seqs[i.toInt()] = x
    }
  }

  var m = 0
  for (i in 1 until seqs.size) {
    if (seqs[i] > seqs[m])
      m = i
  }
  println(m)
}

/**
 * Recursively computes the Collatz number for the given input. Terminates
 * early when an element of the sequence has already been calculated.
 */
fun collatz(s: Long, seqs: Array<Int>): Int {
  if (s < seqs.size && seqs[s.toInt()] != 0) {
    return seqs[s.toInt()]
  }
  if (s == 1L) {
    return 1
  }
  return if (s % 2 == 0L) {
    1 + collatz(s / 2, seqs)
  } else {
    1 + collatz(3 * s + 1, seqs)
  }
}