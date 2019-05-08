
import java.math.BigInteger;

import java.util.Random;

public class maintest {

    public static void main(String[] args) {

        BigInteger num = getNum();
        long numLong = num.longValue();
        System.out.println("Num1: " + num);
        System.out.println("factor: " + factor(numLong));


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

    public static long factor(long num) {
        long i = 2;
        long sqr = (long) Math.sqrt(num);

        while (i < sqr) {
            if (num % i == 0){
                return i;
            }
            else{
                i = i + 2;
            }
        }
        return i;
    }

//    public static BigInteger sqrt(BigInteger x) {
//        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
//        BigInteger div2 = div;
//        // Loop until we hit the same value twice in a row, or wind
//        // up alternating.
//        for(;;) {
//            BigInteger y = div.add(x.divide(div)).shiftRight(1);
//            if (y.equals(div) || y.equals(div2))
//                return y;
//            div2 = div;
//            div = y;
//        }
//    }


    public static BigInteger getNum(){
        Random myRandom = new Random();
        BigInteger prime1 = BigInteger.probablePrime(31, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(31, myRandom);

        return prime1.multiply(prime2);
    }
}
