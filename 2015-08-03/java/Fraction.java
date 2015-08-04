
public class Fraction {

  private final long numerator;
  private final long denominator;

  public Fraction(long numerator, long denominator) {
    long gcd = euklid(numerator, denominator);

    this.numerator = numerator / gcd;
    this.denominator = denominator / gcd;
  }

  public Fraction add(Fraction addend) {
    long num1 = this.getNumerator();
    long num2 = addend.getNumerator();
    long den1 = this.getDenominator();
    long den2 = addend.getDenominator();

    long gcd = euklid(den1, den2);  //greatest common divisor
    long lcm = Math.abs(den1 * den2) / gcd; //least common multiple

    return new Fraction(num1 * (lcm / den1) + num2 * (lcm / den2), lcm);
  }

  private long euklid(long a, long b) {
    if (b == 0) {
      return a;
    }
    return euklid(b, a % b);
  }

  public long getNumerator() {
    return numerator;
  }

  public long getDenominator() {
    return denominator;
  }

  @Override
  public String toString() {
    return numerator + "/" + denominator;
  }
}
