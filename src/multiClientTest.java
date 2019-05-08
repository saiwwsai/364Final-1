import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.math.BigInteger.ZERO;

// square root method: https://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger

public class multiClientTest {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();

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

                    // start send thread
                    HelperThread helpThread = new HelperThread(sock, bigNumStr);
                    Thread newThread = new Thread(helpThread);
                    newThread.start();

                    try {
                        newThread.join();  // wait for newThread to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }

                    long factor = helpThread.getFac();

                    facLst.add(factor); // add all fac from each thread into the facLst

                }

                // todo rest of the format here

                System.out.println("Found factors: " + facLst);

                // send to server
                to.println(facLst);    // todo here

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


        public long getFac(){
            return fac;
        }


        @Override
        public void run() {  // todo synchronized
            // compute the factor
            long numLong = Long.parseLong(numStr);
            this.fac = factor(numLong);
        }
    }


    public static long factor(long num) {
        long i = 2;
        long sqr = (long) Math.sqrt(num);

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
