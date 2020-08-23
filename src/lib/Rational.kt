package lib

import kotlin.math.abs

fun main() {
  println(Rational(2, 3) + Rational(1, 6))
  println(Rational(2, 3) - Rational(1, 6))
  println(Rational(2, 3) * Rational(6, 7))
  println(Rational(2, 3) / Rational(4, 9))
  println(Rational(2, 3) == Rational(4, 6))
  println(Rational(-1, 3))
  println(Rational(1, -3))
  println(Rational(-1, -3))
  println(Rational(1, 4) < Rational(1, 5))
  println(Rational(-3, 4) < Rational(1, -2))
  println(Rational(1, 0))
}

fun Long.toRational(): Rational = Rational(this)

fun abs(q: Rational): Rational = Rational(abs(q.n), q.d)

/**
 * Rational numbers are elements of Z x [Z\0]. Fractions are fun!
 */
data class Rational(private val nIn: Long, private val dIn: Long = 1): Number(), Comparable<Rational> {
  val n: Long
  val d: Long

  init {
    require(dIn != 0L) { "Cannot construct rational with denominator 0: num=$nIn, den=$dIn"}
    val absN = abs(nIn);
    val absD = abs(dIn);
    val gcd = gcd(absN, absD)
    val sig = if ((nIn < 0) == (dIn < 0)) 1 else -1
    n = sig * absN / gcd
    d = absD / gcd
  }

  override fun toString(): String = if (d==1L) n.toString() else "<$n/$d>"

  override fun hashCode(): Int = n.hashCode() * 31 + d.hashCode()

  override fun equals(other: Any?): Boolean =
    if (other is Rational) {
      n*other.d == other.n*d
    }
    else false

  /** Recursive implementation of GCD. Work with reduced values where possible... */
  private fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

  // Operators

  operator fun plus(r: Rational): Rational = Rational(r.d * n + r.n * d, r.d * d)

  operator fun minus(r: Rational): Rational = Rational(r.d * n - r.n * d, r.d * d)

  operator fun times(r: Rational): Rational = Rational(r.n * n, r.d * d)

  operator fun div(r: Rational): Rational = Rational(n * r.d, d * r.n)

  // Comparable

  override fun compareTo(other: Rational): Int = (n * other.d).compareTo(d * other.n)

  // Number

  override fun toByte(): Byte = (n / d).toByte()

  override fun toChar(): Char = (n / d).toChar()

  override fun toDouble(): Double = n.toDouble() / d

  override fun toFloat(): Float = n.toFloat() / d

  override fun toInt(): Int = (n / d).toInt()

  override fun toLong(): Long = n / d

  override fun toShort(): Short = (n / d).toShort()
}