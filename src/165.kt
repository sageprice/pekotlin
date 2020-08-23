import lib.Rational
import lib.abs

/**
 * https://projecteuler.net/problem=165
 *
 * Using the direct approach.
 */
fun main() {
  var s = 290797L
  val mod = 50515093
  val numSteps = 20000
  val points = mutableListOf<Point>()
  for (i in 1..numSteps/2) {
    s = (s * s) % mod
    val t1 = s % 500
    s = (s * s) % mod
    val t2 = s % 500
    points.add(Point(Rational(t1), Rational(t2)))
  }
  val segments = (0 until points.size / 2).map { Line(points[2*it], points[2*it + 1]) }
  val intersections = mutableListOf<Point>()
  for (i in segments.indices) {
    for (j in 0 until i) {
      val intersectionPoint = intersection(segments[i], segments[j])
      intersectionPoint?.let {
        intersections.add(it)
      }
    }
  }
  intersections.sortBy { it.x }
  // Using rational numbers, so need to worry about precision issues.
  val distinctIntersections = intersections.distinct()
  println(distinctIntersections.size)
}

data class Point(val x: Rational, val y: Rational)

data class Line(val p1: Point, val p2: Point) {
  var a: Rational = p2.y - p1.y
  var b: Rational = p1.x - p2.x
  var c: Rational = a * p1.x + b * p1.y

  fun contains(p: Point): Boolean {
    val x = p.x
    val y = p.y
    // True intersection points are interior to both lines.
    if (p == p1 || p == p2) {
      return false
    }
    return ((p1.x <= x && p2.x >= x) || (p1.x >= x && p2.x <= x))
        && ((p1.y <= y && p2.y >= y) || (p1.y >= y && p2.y <= y))
  }
}

// TODO: fix. Intersect calculation is broken.
fun intersection(l1: Line, l2: Line): Point? {
  val det = l1.a*l2.b - l2.a*l1.b
  if (abs(det) == Rational(0)) {
    return null
  } else {
    val x = Rational((l2.b*l1.c - l1.b*l2.c).toLong(), det.toLong())
    val y = Rational((l1.a*l2.c - l2.a*l1.c).toLong(), det.toLong())
    val p = Point(x, y)
    // Verify (x,y) falls within each line segment
    if (l1.contains(p) && l2.contains(p)) {
      return p
    }
    return null
  }
}