
import java.math.BigInteger;

import java.util.Random;

public class maintest {

    public static void main(String[] args) {

        Random myRandom = new Random();

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

//        long prime1 = 924937823;
//        long prime2 = 1125046493;
//
//        long num1 = prime1 * prime2;



        System.out.println(prime1);
        System.out.println(prime2);

        System.out.println("Num1: " + num1);
        System.out.println("factor: " + factor(num1));


    }

//    public static BigInteger factor(BigInteger num){
//
//      //  BigInteger i = new BigInteger("2");
//
//        BigInteger sqr = sqrt(num);
//        BigInteger sqrr = sqrt(sqr);
//        BigInteger i = sqrr.nextProbablePrime();
//
//        while (i.compareTo(sqr) < 0){
//            if (num.mod(i).equals(BigInteger.ZERO)){
//                return i;
//            }
//            else{
//                i = i.nextProbablePrime();
//            }
//        }
//        return null;
//
//    }

    public static long factor(BigInteger num){

        // BigInteger i = new BigInteger("2");

        //   BigInteger sqr = sqrt(num);

        long numLong = num.longValue();

     //   long sqr = sqrt(num).longValue();

        BigInteger i = new BigInteger("2");
//        long i = 2;
//
//
//        while (i < num){
//            if (num % i == 0){
//                return i;
//            }
//            else{
//                BigInteger iBig;
//                iBig = BigInteger.valueOf(i);
//                iBig = iBig.nextProbablePrime();
//                i = iBig.longValue();
//            }
//        }
//        return -1;

        while (i.longValue() < numLong){
            if (numLong % i.longValue() == 0){
                return i.longValue();
            }
            else{
                i = i.nextProbablePrime();
            }
        }
        return -1;

//        while (i.compareTo(num) < 0){
//            if (num.mod(i).equals(BigInteger.ZERO)){
//                return i;
//            }
//            else{
//                i = i.nextProbablePrime();
//            }
//        }
//        return null;
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
