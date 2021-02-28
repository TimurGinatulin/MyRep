package sample.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatNetWorking {
    private static Socket client;
    private static DataOutputStream out;
    private static DataInputStream in;
    public static boolean isLive;

    public ChatNetWorking(String host, int port) {
        isLive = false;
        while (true) {
            if (client != null) {
                if (client.isConnected() && !client.isClosed() && !isLive) {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        isLive = false;
                        throw new RuntimeException("SWW error in networking thread", e);
                    }
                }
            } else {
                try {
                    client = new Socket(host, port);
                    in = new DataInputStream(client.getInputStream());
                    out = new DataOutputStream(client.getOutputStream());
                    System.out.println("Connect");
                    isLive = true;
                } catch (IOException e) {
                    System.out.println("SWW error in connection");
                }
            }
        }
    }

    public static void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            isLive = false;
            throw new RuntimeException("SWW error in sending", e);
        }
    }

    public static String receive() {
        try {
            return in.readUTF();
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }
}
