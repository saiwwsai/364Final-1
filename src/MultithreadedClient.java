//import java.io.*;
//import java.math.BigInteger;
//import java.net.*;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MultithreadedClient {
//    public static void main(String[] args) {
//
//        Scanner kbd = new Scanner(System.in);
//        System.out.print("Enter IP address: ");
//        String ip = kbd.nextLine().trim();
//
//
//        try {
//            Socket sock = new Socket(ip,36911);
//            // a send thread to server
//            SendThread sendThread = new SendThread(sock);
//
//            System.out.println("Connected to " +
//                    sock.getInetAddress());
//
//
//            // press enter to request quote, requesting quote...
//            System.out.println("Press <Enter> to request a quote:");
//
//            // start send thread
//            Thread thread = new Thread(sendThread);
//            thread.start();
//
//            // start receive thread at the same time
//            RecieveThread recieveThread = new RecieveThread(sock);
//            Thread thread2 = new Thread(recieveThread);
//
//            thread2.start();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
//class RecieveThread implements Runnable {
//    Socket sock;
//    BufferedReader from = null;
//    PrintWriter to = null;
//    Scanner kbd = null;
//
//    public RecieveThread(Socket sock) {
//        this.sock = sock;
//    }//end constructor
//
//    public void run() {
//        try{
//            kbd = new Scanner(System.in);
//            from = new BufferedReader(new InputStreamReader(this.sock.getInputStream())); // get local typed message
//            to = new PrintWriter(sock.getOutputStream(), true);
//
//            String s = kbd.nextLine();
//            to.println(s);
//            if (s.isEmpty()){
//                System.out.println("Requesting quote...");
//            }
//
//            // read list of big int from server, fire up same number of threads
//            String numsFrServer = from.readLine();
//
//            String bigNumLst[] = numsFrServer.split(",");
//
//            for (String bigNumStr : bigNumLst){
//
//                //
//
//            }
//        }catch(Exception e){System.out.println(e.getMessage());}
//    }//end run
//}//end class receive thread
//
//class SendThread implements Runnable {
//    Socket sock;
//    PrintWriter print = null;
//    BufferedReader brinput = null;
//    Scanner kbd = new Scanner(System.in);
//
//    public SendThread(Socket sock) {
//        this.sock = sock;
//    }//end constructor
//
//    public void run(){
//        try{
//            if(sock.isConnected()) {
//
//                brinput = new BufferedReader(new InputStreamReader(System.in));   // message typed
//                String msgtoServerString;
//                msgtoServerString = brinput.readLine();             // read the typed message
//
//                print.println(s); // print the empty line (after <Enter>)
//                if (s.isEmpty()){
//                    System.out.println("Requesting quote...");
//                }
//
//
//
//                System.out.println("Client connected to "+sock.getInetAddress() + " on port "+sock.getPort());
//                this.print = new PrintWriter(sock.getOutputStream(), true);  // sent message
//
//                while(true){
//                    System.out.println("Type your message to send to server..type 'EXIT' to exit");
//                    brinput = new BufferedReader(new InputStreamReader(System.in));   // message typed
//                    String msgtoServerString;
//                    msgtoServerString = brinput.readLine();             // read the typed message
//                    this.print.println(msgtoServerString);              // send it to the media
//                    this.print.flush();                                 //
//
//                    if(msgtoServerString.equals("EXIT"))
//                        break;
//                }//end while
//                sock.close();
//            }
//        }
//        catch(Exception e){System.out.println(e.getMessage());
//        }
//    }//end run method
//}//end class