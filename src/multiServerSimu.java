import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;


public class multiServerSimu implements Runnable {
    Socket client;
    String quote = randQuote();
    ArrayList<BigInteger> bigNums = new ArrayList<>();

    multiServerSimu(Socket client) {
        this.client = client;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket sock = new ServerSocket(36911);
        System.out.println("(multi clients server) Waiting for connection ...");

        while (true) {
            Socket client = sock.accept();
            new Thread(new multiServerSimu(client)).start();
        }
    }



    @Override
    public void run() {
        synchronized (this){
            try{
                System.out.println("Connected to " +
                        client.getInetAddress());
                BufferedReader from = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream()
                        )
                );

                PrintWriter to = new PrintWriter(client.getOutputStream(),
                        true);





                while(true){
                    String response = from.readLine();
                    if (response.isEmpty()){
                        System.out.println("Received quote request from Client " + client.getInetAddress() + ".");

                        int rand = new Random().nextInt(4)+1;

                        // append 0-5 numbers of big integers into the list
                        for (int i = 0; i < rand; i ++){
                            BigInteger num = getNum();
                            bigNums.add(num);
                        }
                        System.out.println("Sending " + bigNums + " to Client " + client.getInetAddress() + ".");

                        // pass bigNums to clients
                        to.println(bigNums);



                        while(true){

                            boolean result = true;

                            for (int i = 0; i < bigNums.size(); i++){
                                String fac = from.readLine();

                                System.out.println("Verifying received factor-" + fac + " for number-" + bigNums.get(i)
                                + " for Client " + client.getInetAddress() + ".");

                                long factor = Long.parseLong(fac);
                                if (bigNums.get(i).longValue() % factor == 0){
                                    result = true;
                                }
                                else{
                                    result = false;
                                }
                            }

                            String judge = "\"correct\"";
                            if (result){
                                System.out.println("Sending " + judge + " to Client " + client.getInetAddress() + ".");
                                to.println(judge);
                                System.out.println("Sending quote " + quote + " to Client " + client.getInetAddress() + "...");
                                to.println(quote);
                            }else{
                                judge = "\"incorrect\"";
                                System.out.println(judge + " to Client " + client.getInetAddress() + ".");
                                to.println(judge);
                            }

                        }

                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    public static String randQuote(){
        ArrayList<String> quotes = new ArrayList<String>();
        quotes.add("\"42\"");
        quotes.add("\"A bird in the hand is safer than one overhead.\"");
        quotes.add("\"A clean desk is a sign of a sick mind.\"");
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

    public static BigInteger getNum(){
        Random myRandom = new Random();
        int bigRand = new Random().nextInt(17) + 15;
        BigInteger prime1 = BigInteger.probablePrime(bigRand, myRandom);
        BigInteger prime2 = BigInteger.probablePrime(bigRand, myRandom);

        return prime1.multiply(prime2);
    }
}
