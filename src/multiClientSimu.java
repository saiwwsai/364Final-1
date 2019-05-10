import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

        // declare two threads here to access to them later
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
            while (true) { // make sure it runs "forever"
                System.out.println("Press <Enter> to request a quote:");

                // client's response
                String s = kbd.nextLine();
                to.println(s); // empty line
                // request code if the line is empty
                if (s.isEmpty()){
                    System.out.println("Requesting quote...");
                }

                // read input bigNumber string
                String bigNums = from.readLine();
                System.out.println("Finding factors of " + bigNums);

                // bigNums is passed in as "[123455, 333, 53434]" a string
                // split by the [] and , and save them into an arrayList of big numbers
                ArrayList<String> bigNumLst = new ArrayList<>(Arrays.asList(bigNums.split(",|\\[|\\]")));

                // go through the list and remove the empty strings : formatting
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

                // wait until all the threads are finished
                newThread.join();


                // check if we got a "correct"
                String result;
                result = from.readLine();

                // receive quote
                System.out.println("Received " + result + " from Server!");

                // format console
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

            // keep track of thread number to make sure the threads not mixing up
            int threadNum = 0;

            // formatting
            System.out.println(newThread.getName() + " is running.");

            // calculate the factor
            long factor = factor(numLong);


            // display the factor in list format
            System.out.println(newThread.getName() + " found factor: " + factor);
            // get the thread number
            threadNum = Integer.parseInt(newThread.getName().substring(6));
            // send factor to server
            to.println(factor);
            // send thread number to server for future
            to.println(threadNum);
        }
    }


    public static long factor(long num) {
        long i;
        // integer/double Math.sqrt casted to long
        long sqr = (long) Math.sqrt(num);

        // known that 2 cannot be a factor of num, because num = prime*prime
        i = 3;
        // goes all the way up to sqr root of num, cause we are taking the smallest factor
        while (i < sqr) {
            if (num % i == 0) {
                return i;
            } else {
                // increase by 2, check the odds only
                i = i + 2;
            }
        }
        return i;

    }

}
