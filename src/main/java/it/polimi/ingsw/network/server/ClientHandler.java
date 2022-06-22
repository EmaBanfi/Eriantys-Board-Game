package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.ClientGson;
import it.polimi.ingsw.network.messages.serverMessages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket socket;
    private PrintStream ps = null;
    private final Server server;
    private final int handlerId;
    private final ClientGson clientGson;
    private BufferedReader br = null;
    BufferedReader kb;

    public ClientHandler(Socket socket, Server server, int id) {
        super("ClientHandler");

        this.socket = socket;
        this.server = server;
        handlerId = id;

        try {
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientGson = new ClientGson();
    }

    public synchronized void run() {
        Pong pong = new Pong(this);

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        kb = new BufferedReader(new InputStreamReader(System.in));

        String str;
        pong.start();

        while (!socket.isClosed()) {
            try {
                if ((str = br.readLine()) == null)
                    break;
            } catch (IOException e) {
                System.out.println("The connection is lost. The game is finished.");
                break;
            }

            System.out.println("received by client handler " + handlerId + " from client " + str);
            ClientMessage message = clientGson.deserialize(str);
            message.processMessage(this);

        }

        closeClientConnection();
    }


    public void addToGame(String nick) {
        server.addToGame(nick,this);
        if (handlerId == 1) {
            smAskGameStatus message = new smAskGameStatus("Please choose num of players and mode");
            Gson gson = new Gson();
            String text = gson.toJson(message, smAskGameStatus.class);
            sendMessage(text);
        }
    }

    public void sendMessage(String message){
        System.out.println("send by server: "+message);
        ps.println(message);
    }

    public int getHandlerId(){
        return handlerId;
    }

    public Server getServer() {
        return server;
    }

    public void closeClientConnection() {
        closeHandler();

        server.manageDisconnection(handlerId);

        server.removeClientHandler(this);

        server.removeFromLobby(handlerId);
    }

    public void closeHandler() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ps.close();
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
