import kotlin.math.max
import kotlin.math.pow

/**
 * https://projecteuler.net/problem=277
 *
 * The validity of a sequence of steps is reflected in the base 3 representation of a number. So once we know that the
 * first N steps are valid, we can increment by 3^N to jump straight to the next value for which the first N steps are
 * still valid. Using this approach we can rapidly iterate towards an answer from some starting point. Given that we
 * want the first solution greater than 10^15, we can just start from there.
 */
fun main() {
  val instr = "UDDDUdddDDUDDddDdDddDDUDDdUUDd"

  var s = 1_000_000_000_000_000L
  var executedInstrs: String
  var incPow = 1
  do {
    executedInstrs = getValidInstructions(instr, s)
    println("Started at $s, made it through $executedInstrs")
    if (executedInstrs == instr) break

    incPow = max(incPow, executedInstrs.length)
    s += 3.0.pow(incPow).toLong()
  } while (executedInstrs != instr)
  println(s)
}

private fun getValidInstructions(instructions: String, start: Long): String {
  var validInstructions = ""
  var s = start
  loop@ for (op in instructions) {
    when (s % 3) {
      0L -> {
        if (op == 'D') {
          validInstructions += op
          s /= 3
        } else break@loop
      }
      1L -> {
        if (op == 'U') {
          validInstructions += op
          s = (s * 4 + 2) / 3
        } else break@loop
      }
      2L -> {
        if (op == 'd') {
          validInstructions += op
          s = (s * 2 - 1) / 3
        } else break@loop
      }
    }
  }
  return validInstructions
}