import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

// square root method: https://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger

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
            System.out.println("Connected to " +
                    sock.getInetAddress());
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
                BigInteger fac1 = factor(new BigInteger(num1));
                BigInteger fac2 = factor(new BigInteger(num2));

                System.out.println("Found factors: " + fac1 + ", " + fac2);

                // send to server
                to.println(fac1);
                to.println(fac2);

                // check if we got a "correct"
                String correct = from.readLine();
                // receive quote
                System.out.println("Received " + correct);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static BigInteger factor(BigInteger num){

        BigInteger i = new BigInteger("2");

        //   BigInteger sqr = sqrt(num);

        while (i.compareTo(num) < 0){
            if (num.mod(i).equals(BigInteger.ZERO)){
                return i;
            }
            else{
                i = i.nextProbablePrime();
            }
        }
        return null;

    }

}
