import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class MultiThreadedClient {

    // todo NOTE: cannot run a second round yet because multi server is not complete yet

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();
        // instant factor list
        ArrayList<Long> facLst = new ArrayList<>();

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
                to.println(s);
                if (s.isEmpty()){
                    System.out.println("Requesting quote...");
                }

                // read input bigNumber string
                String bigNums = from.readLine();
                String[] bigNumLst = bigNums.split(",");  // list of str() big integers

                System.out.println("Finding factors of " + bigNums);

                // how many bigNums, fire up how many threads
                for (String bigNumStr : bigNumLst){

                    bigNumStr = bigNumStr.trim(); // "9898365794735959"

                    // start send thread with help from helper thread
                    // pass in the string with Big Nums for the runnable to compute factor
                    HelperThread helpThread = new HelperThread(sock, bigNumStr);
                    // start a new thread for each string(big nums)
                    Thread newThread = new Thread(helpThread);
                    newThread.start();

                    // finish the thread then save the factor
                    try {
                        newThread.join();  // wait for newThread to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }

                    // factor of current big number
                    long factor = helpThread.getFac();
                    // add it to the factor list for further display
                    facLst.add(factor); // add all fac from each thread into the facLst

                }

                // display the factor in list format
                System.out.println("Found factors: " + facLst);

                // send to server
                to.println(facLst);

                // check if we got a "correct"
                String result;
                result = from.readLine();

                // receive quote
                System.out.println("Received " + result);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // a class for runnable, put all the computation under override run
 public static class HelperThread implements Runnable{

        Socket sock = null;
        BufferedReader from = null;
        PrintWriter to = null;
        Scanner kbd = null;

        String numStr;
        private long fac = 0;
     //   ArrayList<BigInteger> facs = new ArrayList<>();


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

           //    this.fac = new BigInteger("111");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // pass the fac to main
        public long getFac(){
            return fac;
        }


        @Override
        public void run() {  // no need to synchronize because we have join()
            // compute the factor
            long numLong = Long.parseLong(numStr);
            this.fac = factor(numLong);
        }
    }


    public static long factor(long num) {
        long i = 2;
        long sqr = (long) Math.sqrt(num);

        // % 2 first, then % all the odds after 2
        while (i < sqr) {
            if (num % i == 0){
                return i;
            }
            else{
                i = i + 2;
            }
        }
        return i;
    }

}
