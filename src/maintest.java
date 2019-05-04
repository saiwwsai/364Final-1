
import java.math.BigInteger;

import java.util.Random;

public class maintest {

    public static void main(String[] args) {

        Random myRandom = new Random();
        // BigInteger(int numBits, Random rnd)
        BigInteger tmp1 = new BigInteger(31, myRandom);
        BigInteger prime1 = tmp1.nextProbablePrime();
        BigInteger tmp2 = new BigInteger(31, myRandom);
        BigInteger prime2 = tmp2.nextProbablePrime();

        BigInteger tmp3 = new BigInteger(31, myRandom);
        BigInteger prime3 = tmp3.nextProbablePrime();
        BigInteger tmp4 = new BigInteger(31, myRandom);
        BigInteger prime4 = tmp4.nextProbablePrime();

        BigInteger num1 = prime1.multiply(prime2);
        BigInteger num2 = prime3.multiply(prime4);


        System.out.println(prime1);
        System.out.println(prime2);
   //     System.out.println(prime3);
    //    System.out.println(prime4);

        System.out.println("Num1: " + num1);
      //  System.out.println(num2);



     //   System.out.println(num1.intValue());
        System.out.println("SQRT: " + sqrt(num1));

        System.out.println("SQRR: " + sqrt(sqrt(num1)));

        System.out.println("Factor: " + factor(num1));

   //     System.out.println(new BigInteger("624597455879094119").mod(new BigInteger("28151")));  // 0
        System.out.println("Test Factor is zero? "+num1.mod(factor(num1))); // 0

  //      System.out.println(new BigInteger("77").mod(new BigInteger("7"))); //0

    }

    public static BigInteger factor(BigInteger num){

      //  BigInteger i = new BigInteger("2");

        BigInteger sqr = sqrt(num);
        BigInteger sqrr = sqrt(sqr);
        BigInteger i = sqrr.nextProbablePrime();

        while (i.compareTo(sqr) < 0){
            if (num.mod(i).equals(BigInteger.ZERO)){
                return i;
            }
            else{
                i = i.nextProbablePrime();
            }
        //   return i;
        }
        return null;

    }


    public static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
}
