
import java.math.BigInteger;

import java.util.Random;

public class maintest {

    public static void main(String[] args) {

        BigInteger num = getNum();
        long numLong = num.longValue();
        System.out.println("Num1: " + num);
        System.out.println("factor: " + factor(numLong));


    }

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




    public static BigInteger getNum(){
        Random myRandom = new Random();
        BigInteger prime1 = BigInteger.probablePrime(31, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(31, myRandom);

        return prime1.multiply(prime2);
    }
}
