import kotlin.math.abs

/**
 * https://projecteuler.net/problem=185
 *
 * Uses simulated annealing https://en.wikipedia.org/wiki/Simulated_annealing
 * to iteratively approach a solution. General algorithm is like so:
 *
 * while s has non-zero error:
 *   change a random char in s
 *   if err(s) < err(best) or best is unchanged for X iterations
 *     best = s
 *
 * Error is defined to be the sum of absolute variation between s and the given
 * restrictions. That is, if 2 chars match between s and r0, but r0 is said to
 * have 4 values correct, then the variation between s and r0 is -2.
 *
 * Due to the random nature of this program, it runs pretty slowly
 */
fun main() {

  val wordScores: List<WordScore> = listOf(
      WordScore("5616185650518293", 2),
      WordScore("3847439647293047", 1),
      WordScore("5855462940810587", 3),
      WordScore("9742855507068353", 3),
      WordScore("4296849643607543", 3),
      WordScore("3174248439465858", 1),
      WordScore("4513559094146117", 2),
      WordScore("7890971548908067", 3),
      WordScore("8157356344118483", 1),
      WordScore("2615250744386899", 2),
      WordScore("8690095851526254", 3),
      WordScore("6375711915077050", 1),
      WordScore("6913859173121360", 1),
      WordScore("6442889055042768", 2),
      WordScore("2321386104303845", 0),
      WordScore("2326509471271448", 2),
      WordScore("5251583379644322", 2),
      WordScore("1748270476758276", 3),
      WordScore("4895722652190306", 1),
      WordScore("3041631117224635", 3),
      WordScore("1841236454324589", 3),
      WordScore("2659862637316867", 2))

  println(anneal("0123456789101112", wordScores))
}

fun anneal(base: String, restrictions: List<WordScore>): String {
  var s = totalScore(base, restrictions)
  var b = base

  var counter = 0
  var lastImprovement = 0
  while (s > 0) {
    counter++

    var b1 = b
    val ind = (0 until base.length).random()
    val c = ('0'..'9').random()
    b1 = b1.replaceRange(ind, ind + 1, c.toString())

    val s1 = totalScore(b1, restrictions)
    // Note: 50 is a rough number which has been found to work, I'm not sure
    // what is the optimal number of changes for which to wait before forcing change.
    if (abs(s1) < abs(s) || counter - lastImprovement > 100) {
      s = s1
      b = b1
      lastImprovement = counter
      // For debugging: print when we make a change
//      println("$counter: $b w/ score $s, ")
    }
  }
  println(counter)
  return b
}


fun totalScore(base: String, restrictions: List<WordScore>): Int {
  return restrictions.map { r -> abs(r.score(base)) }.sum()
}

class WordScore(private val word: String, private val exact: Int) {

  fun score(other: String): Int {
    var exactCount = 0
    for (i in 0 until other.length) {
      if (word[i] == other[i])
        exactCount++
    }
    return exact - exactCount
  }
}