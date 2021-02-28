package sample.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class UserListHandler {
    private final Server server;
    private final Socket client;
    private final ServerSocket serverSocket;
    private final DataOutputStream out;

    public UserListHandler(Socket client, ServerSocket serverSocket, Server server) {
        this.client = client;
        this.serverSocket = serverSocket;
        this.server = server;
        try {
            out = new DataOutputStream(client.getOutputStream());
            getUserList();
        } catch (IOException exception) {
            throw new RuntimeException("SWW in Sending User List");
        }
    }

    private void getUserList() {
        StringBuilder ss = new StringBuilder();
        while (true) {
            if (!server.getHandlers().isEmpty()) {
                for (ClientHandler handler : server.getHandlers()) {
                    ss.append(handler.getLogin())
                            .append("\n");
                }
                try {
                    sendUserList(ss.toString());
                } catch (IOException exception) {
                    break;
                }
                ss.setLength(0);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendUserList(String message) throws IOException {
        out.writeUTF(message);

    }
}
