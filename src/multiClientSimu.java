import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class handle multiple threads (requests) at the same time
 */

public class multiClientSimu {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();


        HelperThread helpThread = null;
        Thread newThread = null;

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

            // start the conversation
            while (true) {
                System.out.println("Press <Enter> to request a quote:");
                // client's response
                String s = kbd.nextLine();
                to.println(s); //empty
                if (s.isEmpty()){
                    System.out.println("Requesting quote...");
                }

                // read input bigNumber string
                String bigNums = from.readLine();
                System.out.println("Finding factors of " + bigNums);

                ArrayList<String> bigNumLst = new ArrayList<>(Arrays.asList(bigNums.split(",|\\[|\\]")));


                for (int i = 0; i < bigNumLst.size(); i ++){
                    String tmp = bigNumLst.get(i);
                    if (tmp.equals("")){
                        bigNumLst.remove(tmp);
                    }
                }

                // how many bigNums, fire up how many threads
                for (int i = 0; i < bigNumLst.size(); i ++){
                    String bigNumStr = bigNumLst.get(i);
                    bigNumStr = bigNumStr.trim();
                    helpThread = new HelperThread(sock, bigNumStr);
                    // start a new thread for each string(big nums)
                    newThread = new Thread(helpThread);
                    newThread.start();
                }

                newThread.join();


                // check if we got a "correct"
                String result;
                result = from.readLine();

                // receive quote
                System.out.println("Received " + result + " from Server!");

                if (result.equals( "\"correct\"")){
                    String quote = from.readLine();
                    // receive quote
                    System.out.println("Received " + quote + " from Server.");
                }
                else{
                    System.out.println("DO IT A AGAIN");
                }
                to.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // a class for runnable, put all the computation under override run
    public static class HelperThread implements Runnable{

        Thread newThread = new Thread();

        Socket sock = null;
        BufferedReader from = null;
        PrintWriter to = null;
        Scanner kbd = null;

        String numStr;
        // constructors: initialization
        public HelperThread(Socket sock, String numStr) {
            this.sock = sock;
            this.numStr = numStr;
            try {
                this.from = new BufferedReader(
                        new InputStreamReader(
                                sock.getInputStream()
                        )
                );

                this.to = new PrintWriter(sock.getOutputStream(),
                        true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {  // no need to synchronize because we have join()
            // compute the factor
            long numLong = Long.parseLong(numStr);
            int threadNum = 0;


            System.out.println(newThread.getName() + " is running.");

            long factor = factor(numLong);


            // display the factor in list format
            System.out.println(newThread.getName() + " found factor: " + factor);
            threadNum = Integer.parseInt(newThread.getName().substring(6));
            // send to server
            to.println(factor);
            to.println(threadNum);
        }
    }


    public static long factor(long num) {
        long i;
        long sqr = (long) Math.sqrt(num);


        i = 3;
        while (i < sqr) {
            if (num % i == 0) {
                return i;
            } else {
                i = i + 2;
            }
        }
        return i;

    }

}
