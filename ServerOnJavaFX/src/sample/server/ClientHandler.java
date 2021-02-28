package sample.server;

import sample.database.DataBaseHandler;
import sample.database.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHandler {
    private final Socket client;
    private final Server server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String login;

    public ClientHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
        try {
            this.out = new DataOutputStream(client.getOutputStream());
            this.in = new DataInputStream(client.getInputStream());
            new Thread(this::listen).start();
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    private void listen() {
        while (true) {
            try {
                String input = in.readUTF();
                if (input.startsWith("/Reg"))
                    doReg(input);
                else {
                    doAuth(input);
                    readMessage();
                }
            } catch (IOException exception) {
                server.printAtServer("lost Connection with " + client);
                break;
            }
        }
        server.unSubscribe(this);
        server.printAtServer(client + " shutdown");
    }

    private void doReg(String input) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        String[] split = input.split("\\s");
        User user = new User(split[1], split[2], split[3], split[4], split[5], split[6]);
        dbHandler.signUpUser(user);

    }

    private void readMessage() {
        String message;
        while (client.isBound()) {
            try {
                message = in.readUTF();
                server.broadcast(login + ": " + message);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void doAuth(String input) {
        String login;
        String password;
        DataBaseHandler dbHandler = new DataBaseHandler();
        while (true) {
            String[] split = input.split("\\s");
            login = split[0];
            password = split[1];
            User user = new User(login, password);
            ResultSet res = dbHandler.getUser(user);
            int count = 0;
            try {
                while (res.next())
                    count++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (server.loginIsEmpty(login)) {
                if (count != 0) {
                    this.login = login;
                    server.subscribe(this);
                    sendMessage("connected");
                    server.printAtServer(this.login + " connected at " + client);
                    break;
                } else sendMessage("User not found");
            } else sendMessage("Login already exist.");
        }
    }

    public String getLogin() {
        return login;
    }
}
