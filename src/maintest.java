
import java.math.BigInteger;

import java.util.Random;

public class maintest {

    public static void main(String[] args) {

        BigInteger num = getNum();
        long numLong = num.longValue();
        System.out.println("Num1: " + num);
        System.out.println("factor: " + factor(numLong));

        String[] bigNumLst = "[3333, 2222]".split(",|\\[|\\]");  // list of str() big integers

//        for (String str : bigNumLst){
//            str = str.trim(); // "9898365794735959"
//            System.out.println("here: " + str);
//        }

    }

    public static long factor(long num) {
        long i = 2;
        long sqr = (long) Math.sqrt(num);

        if (num % 2 == 0){
            return 2;
        }
        else {
            i = 3;
            while (i < sqr) {
                if (num % i == 0) {
                    return i;
                } else {
                    i = i + 2;
                }
            }
        }
        return i;

    }




    public static BigInteger getNum(){
        Random myRandom = new Random();
        BigInteger prime1 = BigInteger.probablePrime(29, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(31, myRandom);

        return prime1.multiply(prime2);
    }
}
