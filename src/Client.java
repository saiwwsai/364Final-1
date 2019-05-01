import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

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
                System.out.println("Waiting ...");
                String response = from.readLine();
                System.out.println("them: " + response);
                System.out.print("me: ");
                String s = kbd.nextLine();
                to.println("Give me a quote: ");
              //  to.println(s);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}