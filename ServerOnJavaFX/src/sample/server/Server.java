package sample.server;

import sample.Controllers.MainSceneServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    ServerConst serverConst = new ServerConst();
    MainSceneServerController msc;
    private ServerSocket server;
    private final Set<ClientHandler> handlers;

    public Server(MainSceneServerController msc) throws IOException {
        this.msc = msc;
        handlers = new HashSet<>();
        new Thread(this::createThreadUserList).start();
        try {
            server = new ServerSocket(serverConst.getPort());
            new Thread(this::updateUserList).start();
            printAtServer("Serer running at local socket address " + server.getLocalSocketAddress() +
                    " inet address " + server.getInetAddress());
            msc.changeStatusIndicator(true);
            while (!server.isClosed()) {
                Socket client = server.accept();
                printAtServer("Client " + client + " accept.");
                new ClientHandler(client, this);
            }
        } catch (Exception e) {
            printAtServer("Error in connection log file." + e);
            msc.changeStatusIndicator(false);
        }
    }

    public void printAtServer(String msg) {
        msc.sendInCMDArea(msg);
    }

    public boolean loginIsEmpty(String login) {
        for (ClientHandler handler : handlers) {
            if (handler.getLogin().equals(login))
                return false;
        }
        return true;
    }

    public void subscribe(ClientHandler handler) {
        this.handlers.add(handler);
    }

    public void unSubscribe(ClientHandler handler) {
        handlers.remove(handler);
    }

    public void updateUserList() {
        while (!server.isClosed()) {
            if (!handlers.isEmpty()) {
                StringBuilder ss = new StringBuilder();
                for (ClientHandler s : handlers) {
                    ss.append(s.getLogin()).append("\n");
                    msc.setUsersArea(ss.toString());
                }
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                msc.setUsersArea("Empty");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void broadcast(String s) {
        for (ClientHandler handler : handlers) {
            handler.sendMessage(s);
        }
    }

    public Set<ClientHandler> getHandlers() {
        return handlers;
    }

    private void createThreadUserList() {
        try {
            ServerSocket userListServer = new ServerSocket(2022);
            while (true) {
                Socket client = userListServer.accept();
                new UserListHandler(client, userListServer, this);
            }
        } catch (IOException exception) {
            exception.printStackTrace();

        }
    }
}
