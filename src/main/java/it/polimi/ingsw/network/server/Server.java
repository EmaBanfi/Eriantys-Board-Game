package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.serverMessages.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server {

    private ServerSocket serverSocket;
    private Controller controller;
    private HashMap<Integer,ClientHandler> lobby;
    private HashMap<String,ClientHandler> clientHandlers;
    private int  id=1;
    private final boolean listeningSocket = true;

    public Server(int port){
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.err.println("Could not listen on port "+port);
        }
        System.out.println("Listening on port "+port+"...");
        lobby = new HashMap<>();
        clientHandlers = new HashMap<>();
        controller = new Controller(this);

        waitForPlayers();
    }
    public Server(){}


    public static void main(String[] args) throws IOException {
        Server server = new Server(888);
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

            int handlerId = newClientHandlerId();
            ClientHandler handler = new ClientHandler(clientSocket,this, handlerId);

            if (handlerId > 4) {
                smLoginFailedMessage message = new smLoginFailedMessage("Lobby is already full");
                Gson gson = new Gson();
                String text = gson.toJson(message, smLoginFailedMessage.class);
                handler.sendMessage(text);
            }

            lobby.put(handlerId, handler);

            if (handlerId == 1) {
                smAskNickname message = new smAskNickname("Please select nickname");
                Gson gson = new Gson();
                String text = gson.toJson(message, smAskNickname.class);
                handler.sendMessage(text);
            }

            else if (lobby.size() > (clientHandlers.size() + 1)) {
                smNotify message = new smNotify("Waiting for player to choose nickname...");
                Gson gson = new Gson();
                String text = gson.toJson(message, smNotify.class);
                handler.sendMessage(text);
            }
            //while(!controller.gameIsSet)
            else if(!controller.gameIsSet()){
                smNotify message = new smNotify("Waiting for host player to set game status...");
                Gson gson = new Gson();
                String text = gson.toJson(message, smNotify.class);
                handler.sendMessage(text);
            }

            else {
                askNickname(false,handler);
            }

            handler.start();
        }
    }

    public void addToGame(String nick, ClientHandler clientHandler) {
        ServerMessage message;
        Gson gson = new Gson();
        if (isAlreadyPresent(nick)) {
            message = new smAskNickname("Nickname already taken. Choose another one ");
            String text = gson.toJson(message, smAskNickname.class);
            clientHandler.sendMessage(text);
            return ;
        }
        message = new smNotify("You have been connected ");
        String text = gson.toJson(message, smNotify.class);
        clientHandler.sendMessage(text);
        clientHandlers.put(nick,clientHandler);
        controller.addPlayerToGame(nick);

        if(controller.gameIsFull()){
            controller.initializePlayersHall();
            controller.notifyGameStatus();
            controller.notifyPlayers();
            controller.notifyStudentsOnIslands();
            controller.notifyMotherNaturePosition();
            if(controller.getGame().isExpertMode())
                controller.notifyAvailableCC();
            //game start
            controller.notifyCurrentPlayer();
        }
        else if ((lobby.size() >= (clientHandlers.size() + 1))&&controller.gameIsSet()) {
            askNickname(true,clientHandler);
        }
    }

    public boolean isAlreadyPresent(String nick){
        return clientHandlers.containsKey(nick);
    }

    public void sendMessage(String nick, String message){
        clientHandlers.get(nick).sendMessage(message);
    }

    public void sendAll(String message){
        for(ClientHandler handler: clientHandlers.values()){
            handler.sendMessage(message);
        }
    }

    public void sendAllExceptPlayer(String nick, String message){
        for(String nickName: clientHandlers.keySet()){
            if(!nickName.equals(nick))
                clientHandlers.get(nickName).sendMessage(message);
        }
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

    public Controller getController() {
        return controller;
    }

    public void removeFromLobby(int id) {
        lobby.remove(id);
    }

    public void removeClientHandler(String nick) {
        clientHandlers.remove(nick);

        if (clientHandlers.isEmpty()) {
            lobby = new HashMap<>();
            clientHandlers = new HashMap<>();
            controller = new Controller(this);
        }
    }

    public void setGameStatus( String mode, int numOfPlayers,ClientHandler clientHandler){
        controller.setGameStatus(mode, numOfPlayers);

        if (lobby.size() >= (clientHandlers.size() + 1)) {
            askNickname(true,clientHandler);
        }
    }

    private void askNickname(boolean nextPlayer, ClientHandler clientHandler){
        smAskNickname message = new smAskNickname(
                "\nGame mode: " + controller.getGame().getMode() +
                "\nNum of players: " + controller.getGame().getNumOfPlayers() +
                "\nPlayers connected: " + clientHandlers.keySet().size()+
                "\nThe following nicknames have already taken: " + alreadyConnectedPlayers() +
                "\nPlease select a nickname");
        Gson gson = new Gson();
        String text = gson.toJson(message, smAskNickname.class);
        if(nextPlayer)
            lobby.get(clientHandler.getHandlerId() + 1).sendMessage(text);
        else
            clientHandler.sendMessage(text);
    }
}
