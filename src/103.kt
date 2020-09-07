/**
 * https://projecteuler.net/problem=103
 */
fun main() {
  val oss6 = arrayOf(11, 18, 19, 20, 22, 25)
  val mid = oss6[(oss6.size+1) / 2]
  val baseOss7 = arrayOf(mid) + oss6.map { it + mid }
  val possibilities = generatePossibilities(baseOss7)
  val allDistinctPossibilities = possibilities.filter { areDistinct(it) }
  val followsRule2 = allDistinctPossibilities.filter { areSbGtSc(it) }
  val followsRule1 = followsRule2.filterNot { containsRepeatedSubsetSum(it) }

  var bestSum = Int.MAX_VALUE
  for (i in followsRule1.indices) {
    if (followsRule1[i].sum() < bestSum) {
      bestSum = followsRule1[i].sum()
      println("New low sum $bestSum with signature " +
          "${followsRule1[i].joinToString(separator="", transform = { it.toString() })}: " +
          followsRule1[i].joinToString { it.toString() })
    }
  }
}

/** Returns true iff all elements of a are distinct values. */
fun areDistinct(a: Array<Int>): Boolean {
  for (i in 0..a.size-2) {
    if (a[i] >= a[i + 1]) return false
  }
  return true
}

/** Returns true iff S(B) > S(C) when |B| > |C| for all B, C which are distinct subsets of A. */
fun areSbGtSc(a: Array<Int>): Boolean {
  for (i in 0..a.size/2) {
    // Slight optimization -- we only need to check the sum of the
    // smallest i+1 elements compared to the largest i elements, as
    // this combo should cause the largest difference.
    val bs = a.sliceArray(0..i).sum()
    val cs = a.sliceArray(a.size - i until a.size).sum()
    if (bs <= cs) return false
  }
  return true
}

/** Returns true iff xs contains two subsets whose elements sum to the same value. */
private fun containsRepeatedSubsetSum(
    xs: Array<Int>,
    i: Int = 0,
    subset: MutableList<Int> = mutableListOf(),
    subsetSums: MutableSet<Int> = mutableSetOf()): Boolean {
  if (i == xs.size) {
    return if (subsetSums.contains(subset.sum())) {
      true
    } else {
      subsetSums.add(subset.sum())
      false
    }
  }
  // Case I: integer at index is included.
  subset.add(xs[i])
  if (containsRepeatedSubsetSum(xs, i + 1, subset, subsetSums)) {
    return true
  }
  subset.removeLast()
  // Case II: integer at index is excluded.
  return containsRepeatedSubsetSum(xs, i + 1, subset, subsetSums)
}

/**
 * Returns a list of int sets which may satisfy problem 103 constraints.
 * @param range by which each value may differ from the input set's value at at index. E.g. if the input set has first
 *        element 2, and range is 3, then possibilities for the first element are [-1, 0, 1, 2, 3, 4, 5].
 */
private fun generatePossibilities(set: Array<Int>, range: Int = 3): List<Array<Int>> {
  var variations = listOf(set)
  var absRange = if (range < 0) -range else range
  for (i in set.indices) {
    if (i == set.size-1) absRange+=2
    val decs = (-absRange..-1).map { x ->
      val newVars = variations.map { it.copyOf() }
      newVars.forEach { it[i] += x }
      newVars
    }.flatten()

    val incs = (1..absRange).map { x ->
      val newVars = variations.map { it.copyOf() }
      newVars.forEach { it[i] += x }
      newVars
    }.flatten()
    variations = variations + decs + incs

  }
  variations.forEach { it.sort() }
  return variations
}