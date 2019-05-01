import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIGlobalBinding;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class maintest {

    public static void main(String[] args) {

        Random myRandom = new Random();
        // BigInteger(int numBits, Random rnd)
        BigInteger tmp1 = new BigInteger(20, myRandom);
        BigInteger prime1 = tmp1.nextProbablePrime();
        BigInteger tmp2 = new BigInteger(20, myRandom);
        BigInteger prime2 = tmp2.nextProbablePrime();

        BigInteger tmp3 = new BigInteger(10, myRandom);
        BigInteger prime3 = tmp3.nextProbablePrime();
        BigInteger tmp4 = new BigInteger(10, myRandom);
        BigInteger prime4 = tmp4.nextProbablePrime();

        BigInteger num1 = prime1.multiply(prime2);
        BigInteger num2 = prime3.multiply(prime4);

        System.out.println(prime1);
        System.out.println(prime2);
        System.out.println(prime3);
        System.out.println(prime4);

        System.out.println(num1);
        System.out.println(num2);



/*
        BigInteger i = new BigInteger("2");

        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

       // get a list of factors
        while (i.compareTo(bigInteger) < 0){
            // if bigInteger % i == 0, it is a factor, add to the list
            if (bigInteger.mod(i).equals(BigInteger.ZERO)){
                factors.add(i);
                i = i.nextProbablePrime();
            }else{
                i = i.nextProbablePrime();
            }
        }

        Random factorRan = new Random();*/
       // System.out.println(factors.get(factorRan.nextInt(factors.size())));

 /*       BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        from = new BufferedReader(
                new InputStreamReader(
                        client.getInputStream()
                )
        );

        to = new PrintWriter(client.getOutputStream(),
                true);
*/



    }
}
