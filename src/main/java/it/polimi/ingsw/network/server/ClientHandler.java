package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.ClientTimer;
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
    //private ServerTimer serverTimer;

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

       // serverTimer = new ServerTimer(this);
        // serverTimer.start();
    }

    public synchronized void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        String str = null;

        while (true) {
            //serverTimer.resetWaitingTime();

            try {
                if ((str = br.readLine()) == null)
                    break;
            } catch (IOException e) {
                System.out.println("The connection is lost. The game is finished.");

                System.exit(-1);
            }
            if(str!=null) {
                System.out.println("received by client handler " + handlerId + " from client " + str);
                ClientMessage message = clientGson.deserialize(str);
                message.processMessage(this);
            }
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

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
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

    public void closeClientConnection(String nick) {
        server.removeFromLobby(handlerId);

        server.removeClientHandler(nick);
    }

    public void manageDisconnection() {
        String text = "The game will be closed because the connection with player " + server.getNickByHandlerId(handlerId) + " is lost";

        Gson gson = new Gson();
        smCloseThemAll message = new smCloseThemAll(text);
        text = gson.toJson(message, smCloseThemAll.class);

        getServer().sendAll(text);

        System.out.println("The connection is lost. The game is finished.");

        System.exit(-1);
    }
}
