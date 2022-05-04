package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server{

    ServerSocket serverSocket;
    private Game game;
    private HashMap<Integer,ClientHandler> lobby;
    private HashMap<String,ClientHandler> clientHandlers;
    private int  id=1;
    boolean listeningSocket = true;
    private boolean  acceptingConnection=true;

    public Server(int port){
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Connection established");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
        }

        lobby=new HashMap<>();
        clientHandlers = new HashMap<>();
        game=new Game(this);

        waitForPlayers();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput=true;

        System.out.println("Insert the port which server will listen on.");
        int port=0;

        do {
            try {
                port = scanner.nextInt();
            } catch (InputMismatchException e) {
                validInput = false;
                System.out.println("The value must be a number");
            }
            if (port < 0 || port > 1024){
                validInput = false;
                System.out.println("The port number must be positive and minor than 1024");
            }
        } while (!validInput);

        Server server = new Server(port);
    }

    private synchronized Integer newClientHandlerId(){
        int i = id;
        id++;
        return i;
    }

    public void notifyAll(String message) {
        for (String nick : clientHandlers.keySet()) {
            clientHandlers.get(nick).sendMessage(message);
        }
    }

    public void waitForPlayers() {
         while (listeningSocket) {
             Socket clientSocket = null;
             try {
                 clientSocket = serverSocket.accept();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             int handlerId=newClientHandlerId();
             ClientHandler handler = new ClientHandler(clientSocket,this,handlerId);

             if (handlerId > 4) {
                 LoginFailedMessage message = new LoginFailedMessage("Lobby is already full");
                 Gson gson = new Gson();
                 String text = gson.toJson(message, Message.class);
                 handler.sendMessage(text);
             }

             lobby.put(handlerId,handler);

             if (handlerId == 1) {
                 Message message = new Message("Please select nickname");
                 Gson gson = new Gson();
                 String text = gson.toJson(message, Message.class);
                 handler.sendMessage(text);
             }

             if (lobby.size() > (clientHandlers.size() + 1)) {
                 Notify message = new Notify("Waiting for player to choose nickname...");
                 Gson gson = new Gson();
                 String text = gson.toJson(message, Notify.class);
                 handler.sendMessage(text);
             }

             if (handlerId >= 2 && (lobby.size() == clientHandlers.size() + 1))  {
                 Message message = new Message("Please select nickname. The following nicknames have already taken " + alreadyConnectedPlayers());
                 Gson gson = new Gson();
                 String text = gson.toJson(message, Message.class);
                 handler.sendMessage(text);
             }

             handler.start();
         }
    }

    public boolean addToGame(String nick, ClientHandler clientHandler) {
        if (clientHandlers.containsKey(nick))
            return false;

        clientHandlers.put(nick,clientHandler);
        if(lobby.size() >= (clientHandlers.size() + 1)) {

            Message message = new Message("Please select nickname. The following nicknames have already taken " + alreadyConnectedPlayers());
            Gson gson = new Gson();
            String text = gson.toJson(message, Message.class);
            lobby.get(clientHandler.getHandlerId() + 1).sendMessage(text);
        }

        return true;
    }

    public boolean isAlreadyPresent(String nick){
        return clientHandlers.containsKey(nick);
    }

    public Game getGame(){
        return game;
    }

    public void sendMessage(String nick, String message){
        clientHandlers.get(nick).sendMessage(message);
    }

    private String alreadyConnectedPlayers(){
        String nicks = "";
        for (String nickname : clientHandlers.keySet()) {
            if (nicks.length() == 0) {
                nicks = nickname;
            } else
                nicks = nicks + ", " + nickname;
        }
        return nicks;
    }

}
