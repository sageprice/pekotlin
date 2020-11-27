private val ZERO = '0'
private val ONE = '1'
private val N = 5
private val LEN = 32

/**
 * https://projecteuler.net/problem=265
 *
 * Basically using the construction logic for a DeBruijn sequence.
 */
fun main() {
  val sequences = mutableSetOf<String>()
  generateSequences(ordering = "00000", sequences = sequences)
  println(seqSum(sequences))
//  println(sequences.size)
}

fun generateSequences(
    ordering: String,
    visited: Set<String> = setOf(ordering),
    sequences: MutableSet<String> = mutableSetOf()) {
  // Note: we have to check the wraparound.
  if (ordering.length == LEN && !visited.contains(ordering.takeLast(1) + ordering.take(N-1))) {
    sequences.add(ordering)
    return
  }
  val addZero = ordering + ZERO
  val tailZero = addZero.takeLast(N)
  if (!visited.contains(tailZero)) {
    generateSequences(addZero, visited + tailZero, sequences)
  }
  val addOne = ordering + ONE
  val tailOne = addOne.takeLast(N)
  if (!visited.contains(tailOne)) {
    generateSequences(addOne, visited + tailOne, sequences)
  }
}

fun seqSum(seqs: Iterable<String>): Long {
  return seqs.map { it.toLong(2) }.sum()
}