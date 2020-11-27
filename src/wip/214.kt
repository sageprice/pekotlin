package wip

/**
 * https://projecteuler.net/problem=214
 */
fun main() {
//  val lim = 40_000_000
//  val targetChainLength = 25
  val lim = 20
  val targetChainLength = 4
  val factors = Array<MutableList<Int>>(lim+1) { mutableListOf() }
  factors[1].add(1)
  println("Adding 2s...")
  for (i in 2..lim step 2) {
    factors[i].add(2)
  }
  println("Done adding 2s...")
  for (i in 3..lim step 2) {
    if (i % 10_000 == 1) println("Iteration progress: $i")
    if (factors[i].isEmpty()) {
      factors[i].add(i)
      for (j in i*i .. lim step 2*i) {
        factors[j].add(i)
      }
    }
  }
  println("Done calculating factors")
  val chainLengths = mutableMapOf(Pair(1,1))
  var solutions = 0
  var solutionSum = 0
  for (i in 3..lim step 2) {
    if (factors[i][0] == i) {
      val cl = calculateChainLength(i, factors, chainLengths)
      if (cl == targetChainLength) {
        println(i)
        solutions++
        solutionSum += i
      }
    }
  }
  println("Number of primes with chain length 25 is: $solutions")
}

fun calculateChainLength(x: Int, factors: Array<MutableList<Int>>, chainLengths: MutableMap<Int, Int>): Int {
  if (chainLengths[x] != 0) return chainLengths[x]!!
  val fs = factors[x]
  var next = x
  for (f in fs) {
    next /= f
    next *= f-1
  }
  val cl = 1 + calculateChainLength(next, factors, chainLengths)
  chainLengths[x] = cl
  return cl
}