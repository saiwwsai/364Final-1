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

public class MultiThreadedServer {

    public static void main(String[] args) {
        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        ArrayList<String> quotes = new ArrayList<String>();
        quotes.add("\"42\"");
        quotes.add("A bird in the hand is safer than one overhead.");
        quotes.add("A clean desk is a sign of a sick mind.");
        quotes.add("A computer makes as many mistakes in one second as three people working for thirty years straight.");
        quotes.add("A conference is simply an admission that you want somebody else to join you in your troubles.");
        quotes.add("A dog is a dog except when he is facing you. Then he is Mr. Dog.");
        quotes.add("A great deal of money is never enough once you have it.");
        quotes.add("A major failure will not occur until after the unit has passed final inspection.");
        quotes.add("A meeting is an event at which the minutes are kept and the hours are lost.");
        quotes.add("A misplaced decimal point will always end up where it will do the greatest damage.");
        quotes.add("A perfectly calm day will turn gusty the instant you drop a $20 bill.");
        quotes.add("A stockbroker is someone who invests your money until it is all gone.");
        quotes.add("A synonym is a word you use when you can't spell the other one.");
        quotes.add("A waist is a terrible thing to mind.");

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

                BigInteger num1 = getNum();
                BigInteger num2 = getNum();

                int randomNum = new Random().nextInt(quotes.size()-1);
                String ranQuote = quotes.get(randomNum);


                String response = from.readLine();

                if (response.isEmpty()){
                    System.out.println("Received quote request from client!");
                    System.out.println("Sending " + num1 + ", " + num2 + " to Client.");

                    // pass num1 and num2 to the media
                    //to.println(num1);
                    //to.println(num2);
                    String tmp= num1 + ", " + num2;
                    to.println(tmp);


                    // received factors from client
                    String fac = from.readLine();


                    System.out.println("Received factor:" + fac);

                    System.out.println("Verifying factors...");

                    // verify the factor
                    BigInteger fac1num = new BigInteger(fac);
                    BigInteger fac2num = new BigInteger(fac);
                    String correct = "\"correct\"";

                    // verify the factor by mod it get
                    if (num1.mod(fac1num).equals(BigInteger.ZERO)) {

                        System.out.println("Sending " + correct);
                        to.println(correct);
                        System.out.println("Sending quote " + ranQuote);
                        to.println(ranQuote);
                    }
                    else{
                        correct = "incorrect";
                        System.out.println(correct);
                        to.println(correct);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger getNum(){
        Random myRandom = new Random();
        BigInteger prime1 = BigInteger.probablePrime(31, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(31, myRandom);

        return prime1.multiply(prime2);
    }
}