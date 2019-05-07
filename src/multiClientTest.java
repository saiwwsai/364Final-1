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

        ArrayList<BigInteger> facLst = new ArrayList<>();

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

                    bigNumStr = bigNumStr.trim();

                    // start send thread
                    HelperThread helpThread = new HelperThread(sock, bigNumStr);
                    Thread newThread = new Thread(helpThread);
                    newThread.start();

                    BigInteger faccc = helpThread.getFac(8); // one fac
                    facLst.add(faccc); // add all fac from each thread into the facLst

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
        BigInteger fac;
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

               this.fac = new BigInteger("111");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//        public BigInteger getFac(){
//            System.out.println("HERE111: " +fac);
//            return fac;
//        }

        @Override
        public void run() {
            // compute the factor

            fac = factor(new BigInteger(numStr));
            System.out.println("HERE: " +fac);
            int tmp = 8;
            getFac(tmp);

        }

        public BigInteger getFac(int tmp){
            System.out.println("HERE111: " +fac);
            return fac;
        }
    }



    public static BigInteger factor(BigInteger num){

        BigInteger i = new BigInteger("2");

        //   BigInteger sqr = sqrt(num);

        while (i.compareTo(num) < 0){
            if (num.mod(i).equals(ZERO)){
                return i;
            }
            else{
                i = i.nextProbablePrime();
            }
        }
        return null;

    }

}
