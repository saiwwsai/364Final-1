import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        ArrayList<String> quotes = new ArrayList<String>();
        quotes.add("\"42\"");
        quotes.add("\"43\"");
        int randomNum = new Random().nextInt(quotes.size()-1);
        String ranQuote = quotes.get(randomNum);


        BigInteger num1 = getNum();
        BigInteger num2 = getNum();


        // todo this is where big integer gets generated

        try {
            sock = new ServerSocket(36911);
            System.out.println("Waiting for connection ...");
            client = sock.accept();
            System.out.println("Connected to " +
                    client.getInetAddress());
            from = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()
                    )
            );

            to = new PrintWriter(client.getOutputStream(),
                    true);

            while (true) {

                String response = from.readLine();

                if (response.isEmpty()){
                    System.out.println("Received quote request from client!");
                    System.out.println("Sending " + num1 + ", " + num2 + " to Client.");

                    // pass num1 and num2 to the media
                    to.println(num1);
                    to.println(num2);


                    // received factors from client
                    String fac1 = from.readLine();
                    String fac2 = from.readLine();
                    System.out.println("Received factor:" + fac1 + "," + fac2);

                    System.out.println("Verifying factors...");

                    // verify the factor
                    BigInteger fac1num = new BigInteger(fac1);
                    BigInteger fac2num = new BigInteger(fac2);
                    String correct = "\"correct\"";

                    // verify the factor by mod it get
                    if (num1.mod(fac1num).equals(BigInteger.ZERO)){
                        if (num2.mod(fac2num).equals(BigInteger.ZERO)){
                            System.out.println("Sending " + correct);
                            to.println(correct);
                            System.out.println("Sending quote " + ranQuote);
                            to.println(ranQuote);
                        }else{
                            correct = "incorrect";
                            System.out.println(correct);
                            to.println(correct);
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger getNum(){
        Random myRandom = new Random();
        // BigInteger(int numBits, Random rnd)
        BigInteger tmp1 = new BigInteger(16, myRandom);
        BigInteger prime1 = tmp1.nextProbablePrime();
        BigInteger tmp2 = new BigInteger(31, myRandom);
        BigInteger prime2 = tmp2.nextProbablePrime();

        return prime1.multiply(prime2);
    }
}