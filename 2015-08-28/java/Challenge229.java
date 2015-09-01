import java.math.BigInteger;

public class Challenge229 {

  public static void main(String[] args) {
    for (int exponent : new int[]{11}) {
      System.out.println("result for 10^" + exponent + " : " + new Challenge229(exponent).compute());
    }
  }

  private final int exponent;

  public Challenge229(int exponent) {
    this.exponent = exponent;
  }

  public String compute() {

    BigInteger result = BigInteger.ZERO;
    BigInteger interval = new BigInteger("7");
    BigInteger targetNumber = BigInteger.TEN.pow(exponent);

    for (BigInteger current = BigInteger.ZERO; current.compareTo(targetNumber) <= 0; current = current.add(interval)) {
      BigInteger reverseNumber = new BigInteger(reverseString(current.toString()));
      if (reverseNumber.remainder(interval) == BigInteger.ZERO) {
        result = result.add(current);
      }
    }

    return result.toString();
  }

  private String reverseString(String source) {
    char[] reverse = new char[source.length()];
    int size = source.length();
    for (int n = size - 1; n >= 0; n--) {
      reverse[size - n - 1] = source.charAt(n);
    }
    return new String(reverse);
  }
}
