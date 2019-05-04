import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MultithreadedClient implements Runnable{


    /** The client connection of Socket. */
    protected Socket connection;

    /** The request pool. */
    protected static List pool = new LinkedList();

    /**
     * Instantiates a new pooled connection handler.
     */
    public MultithreadedClient() {
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (true) {
            // 因为可能有多个线程同时去Pool中取Socket进行处理。
            // 所以这里我们需同步，防止同一个请求被多次处理
            synchronized (pool) {
                while (pool.isEmpty()) {
                    try {
                        pool.wait();// 没有请求到来则等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 从池中取出一个Socket，准备进行处理
                connection = (Socket) pool.remove(0);
            }
            // 取到Socket后则不需要同步了，因为此时是Connection是对象
            // 级属性，在线程内部自己处理，不涉及公共资源的访问
            handleConnection();
        }
    }

    /**
     * Process request, append Socket to pool and notify all waitting thread
     *
     * @param requestToHandle the request to handle
     */
    public static void processRequest(Socket requestToHandle) {
        // 因为有可能在向池中塞请求的时候，另外一个线程
        // 正在从池中取Socket，所以这里需要同步一下
        synchronized (pool) {
            // 将来自客户端的请求添加到请求队列末尾
            pool.add(pool.size(), requestToHandle);
            // 通知其它正在等待的线程有新请求来到，
            // 此时所有处于wait状态的线程将被唤醒
            pool.notifyAll();
        }
    }

    /**
     * Handle connection.
     */
    public void handleConnection() {
        try {
            PrintWriter streamWriter = new PrintWriter(connection
                    .getOutputStream());
            BufferedReader streamReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String fileToRead = streamReader.readLine();
            BufferedReader fileReader = new BufferedReader(new FileReader(
                    fileToRead));

            String line = null;
            while ((line = fileReader.readLine()) != null)
                streamWriter.println(line);

            fileReader.close();
            streamWriter.close();
            streamReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("");
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }
}
