import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

public class MultiClientServer{

    // todo this class handle multiple clients connect at the same time

    public static void main(String[] args) {
        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        System.out.println("(multi-client server) Waiting for connection ...");


        try {
            sock = new ServerSocket(36911);

            client = sock.accept();


            MultiClientServer.ServerHelper helpThread = new MultiClientServer.ServerHelper(client);
            // start a new thread for each string(big nums)
            Thread newThread = new Thread(helpThread);
            newThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static BigInteger getNum(){
        Random myRandom = new Random();
        int bigRand = new Random().nextInt(17) + 15;
        BigInteger prime1 = BigInteger.probablePrime(bigRand, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(bigRand, myRandom);

        return prime1.multiply(prime2);
    }


    public static String randQuote(){
        ArrayList<String> quotes = new ArrayList<String>();
        quotes.add("\"42\"");
        quotes.add("\"A bird in the hand is safer than one overhead.\"");
        quotes.add("\"A clean desk is a sign of a sick mind.");
        quotes.add("\"A computer makes as many mistakes in one second as three people working for thirty years straight.\"");
        quotes.add("\"A conference is simply an admission that you want somebody else to join you in your troubles.\"");
        quotes.add("\"A dog is a dog except when he is facing you. Then he is Mr. Dog.\"");
        quotes.add("\"A great deal of money is never enough once you have it.\"");
        quotes.add("\"A major failure will not occur until after the unit has passed final inspection.\"");
        quotes.add("\"A meeting is an event at which the minutes are kept and the hours are lost.\"");
        quotes.add("\"A misplaced decimal point will always end up where it will do the greatest damage.\"");
        quotes.add("\"A perfectly calm day will turn gusty the instant you drop a $20 bill.\"");
        quotes.add("\"A stockbroker is someone who invests your money until it is all gone.\"");
        quotes.add("\"A synonym is a word you use when you can't spell the other one.\"");
        quotes.add("\"A waist is a terrible thing to mind.\"");

        // generate a random quote
        int randomNum = new Random().nextInt(quotes.size()-1);

        return quotes.get(randomNum);
    }


    public static class ServerHelper implements Runnable{

        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        public ServerHelper (Socket client){
            this.client = client;

        }




        @Override
        public void run() {
            try {
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
                    // generate a random-sized(0-5) list of big Integers
                    // random.nextInt(max - min + 1) + min
                    int rand = new Random().nextInt(4)+1;
                    ArrayList<BigInteger> bigNums = new ArrayList<>();

                    // get random quote
                    String ranQuote = randQuote();

                    // append 0-5 numbers of big integers into the list
                    for (int i = 0; i < rand; i ++){
                        BigInteger num = getNum();
                        bigNums.add(num);
                    }




                    // start if <Enter> is pressed by client
                    String response = from.readLine();
                    if (response.isEmpty()){
                        System.out.println("Received quote request from Client " + client.getInetAddress() + "!");
                        System.out.println("Sending " + bigNums + " to Client " + client.getInetAddress() + ".");

                        // pass bigNums to clients
                        to.println(bigNums);


                        // received factors from client, got a list format
                        String factors = from.readLine();

                        // print them in list format
                        System.out.println("Received factor:" + factors + "from Client " + client.getInetAddress());

                        System.out.println("Verifying factors for Client " + client.getInetAddress() + "...");

                        // verify the factor
                        // transform the "[numbers]" into longs for further computation
                        ArrayList<String> facLst = new ArrayList<>(Arrays.asList(factors.split(",|\\[|\\]")));
                        long fac = 0;
                        boolean result = false;

                        for (int i = 0; i < facLst.size(); i ++){
                            String tmp = facLst.get(i);
                            if (tmp.equals("")){
                                facLst.remove(tmp);
                            }
                        }

                        for (int i = 0; i < facLst.size(); i ++ ){
                            // this is one factor
                            String facStr = facLst.get(i).trim();
                            fac = Long.parseLong(facStr);

                            // this is the corresponding num at the same position
                            long num = bigNums.get(i).longValue();

                            // verify the factor by mod it get
                            if (num % fac == 0) {
                                result = true;
                            }
                            else{
                                result = false;
                                break;
                            }
                        }
                        String judge = "\"correct\"";
                        if (result){
                            System.out.println("Sending " + judge + " to Client " + client.getInetAddress() + ".");
                            to.println(judge);
                            System.out.println("Sending quote " + ranQuote + " to Client " + client.getInetAddress() + "...");
                            to.println(ranQuote);
                        }else{
                            judge = "\"incorrect\"";
                            System.out.println(judge + " to Client " + client.getInetAddress() + ".");
                            to.println(judge);
                        }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}

