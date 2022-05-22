package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.CmGson;
import it.polimi.ingsw.network.messages.serverMessages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private final Socket socket;
    private PrintStream ps = null;
    private final Server server;
    private final int handlerId;
    private final CmGson cmGson;

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

        cmGson = new CmGson();
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
        String str1 = null;

        while (true) {
            try {
                if ((str = br.readLine()) == null)
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(str!=null) {
                //System.out.println("Client" + handlerId + ": " + str);

                ClientMessage message = cmGson.deserialize(str);
                message.processMessage(this);
            }

            //mettere a posto riceve messaggi (non primo)
            //ps.printf(server.getGame().answer());
            /*try {
                str1 = kb.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ps.println(str1);*/
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
        if (!server.addToGame(nick,this)) {
            smAskNickname message = new smAskNickname("Nickname already taken. Choose another one ");
            Gson gson = new Gson();
            String text = gson.toJson(message, smAskNickname.class);
            sendMessage(text);
        } else {
            Gson gson = new Gson();
            smNotify notify;
            if (server.isAlreadyPresent(nick)) {
                notify = new smNotify("You have been connected");
            } else {
                notify = new smNotify("Not connected");
            }
            String text = gson.toJson(notify, smNotify.class);
            sendMessage(text);
        }
        if (handlerId == 1) {
            smAskGameStatus message = new smAskGameStatus("Please choose num of players and mode");
            Gson gson = new Gson();
            String text = gson.toJson(message, smAskGameStatus.class);
            sendMessage(text);
        }
    }

    public void sendMessage(String message){
        System.out.println(message);
        ps.println(message);
    }

    public int getHandlerId(){
        return handlerId;
    }

}
