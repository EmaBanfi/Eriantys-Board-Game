package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private Socket socket = null;
    PrintStream ps = null;
    private final Server server;
    private boolean firstMessage=true;
    private int handlerId;

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

    }

    public synchronized void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String str = null;
            String str1 = null;

            while (true) {
                try {
                    if (!((str = br.readLine()) != null))
                        break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Client" + handlerId + ": " + str);

                Gson gson = new Gson();
                ClientFirstMessage message = gson.fromJson(str, ClientFirstMessage.class);
                message.processMessage(this);

                //mettere a posto riceve messaggi (non primo)
                ps.printf(server.getGame().answer());
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

    }

    public void addToGame(String nick) {
        if (server.isAlreadyPresent(nick)) {
            Message message = new Message("Nickname already taken. Choose another one ");
            Gson gson = new Gson();
            String text = gson.toJson(message, Message.class);
            sendMessage(text);
        } else {
            server.addToGame(nick, this);

            Gson gson = new Gson();
            Notify notify;
            if (server.isAlreadyPresent(nick)) {
                notify = new Notify("You have been connected");
            } else {
                notify = new Notify("Not connected");
            }
            String text = gson.toJson(notify, Notify.class);
            sendMessage(text);
        }
    }

    public void sendMessage(String message){
        ps.println(message);
    }

    public int getHandlerId(){
        return handlerId;
    }

}
