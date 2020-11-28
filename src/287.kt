import kotlin.math.pow

private const val N = 24
private val c = 2.0.pow(N-1).toLong()
private val lim = c * c

/**
 * https://projecteuler.net/problem=287
 *
 * D&C. Check the corners and center -- if they're all the same color then the whole square is the same color by virtue
 * of the black/white function being a circle and our quartering procedure.
 */
fun main() {
  println(encodeSquare(c.toInt(), c.toInt(), c.toInt()*2))
}

fun encodeSquare(x: Int, y: Int, width: Int): Long {
  // Check the corners, if they're all the same as the center then the whole thing is one color.
  val pointColors = arrayOf(
      isBlack(x - width / 2, y + width / 2 - 1), // tl
      isBlack(x + width / 2 - 1, y + width / 2 - 1), // tr
      isBlack(x - width / 2, y - width / 2), // bl
      isBlack(x + width / 2 - 1, y - width / 2), // br
      isBlack(x, y))                                  // center
  if (pointColors.all { it } || pointColors.all { !it }) {
    return 2
  }
  if (width == 2) { // base case, we must represent each pixel explicitly
    return 9
  }
  val nextWidth = width / 2
  val inc = width / 4
  return 1 + // '0' split bit
      encodeSquare(x - inc, y + inc, nextWidth) + // tl
      encodeSquare(x + inc, y + inc, nextWidth) + // tr
      encodeSquare(x - inc, y - inc, nextWidth) + // bl
      encodeSquare(x + inc, y - inc, nextWidth) // br
}

fun isBlack(x: Int, y: Int): Boolean {
  return (x-c)*(x-c) + (y-c)*(y-c) <= lim
}