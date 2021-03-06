import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class handle single threaded communication
 */
public class Client {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();

        try {
            sock = new Socket(ip, 36911);
            System.out.println("Connected to " + sock.getInetAddress());

            from = new BufferedReader(
                    new InputStreamReader(
                            sock.getInputStream()
                    )
            );
            to = new PrintWriter(sock.getOutputStream(),
                    true);



            while (true) {

                System.out.println("Press <Enter> to request a quote:");
                // client's response
                String s = kbd.nextLine();
                to.println(s);
                if (s.isEmpty()){
                    System.out.println("Requesting quote...");
                }

                // read num1 and num2 from media
                String num1 = from.readLine();
                String num2 = from.readLine();

                // print "Received factor xxxx, xxxxx"
                System.out.println("Finding factors of " + num1 +", " + num2);

                // find the factor here:
//                BigInteger fac1 = factor(new BigInteger(num1));
//                BigInteger fac2 = factor(new BigInteger(num2));

                long fac1 = factor(new BigInteger(num1));
                long fac2 = factor(new BigInteger(num2));

                System.out.println("Found factors: " + fac1 + ", " + fac2);

                // send to server
                to.println(fac1);
                to.println(fac2);

                // check if we got a "correct"
                String correct = from.readLine();
                // receive quote
                System.out.println("Received " + correct + "from server");

                String ranQuote = from.readLine();
                System.out.println("Received quote: " + ranQuote);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static long factor(BigInteger num){

       // BigInteger i = new BigInteger("2");

        //   BigInteger sqr = sqrt(num);

        long numLong = num.longValue();

        BigInteger i = new BigInteger("2");

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

}

