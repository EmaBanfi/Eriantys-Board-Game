package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.serverMessages.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    private Controller controller;
    private HashMap<Integer,ClientHandler> lobby;
    private HashMap<String,ClientHandler> clientHandlers;

    private  Gson gson;

    private int  id=1;
    private int maxPlayers = 4;

    public Server(int port){
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.err.println("\nCould not listen on port "+port);
        }
        System.out.println("\nListening on port "+port+"...\n");
        lobby = new HashMap<>();
        clientHandlers = new HashMap<>();
        controller = new Controller(this);
        gson = new Gson();
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

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int handlerId = newClientHandlerId();
            ClientHandler handler = new ClientHandler(clientSocket,this, handlerId);

            if (handlerId > maxPlayers) {
                smLoginFailedMessage message = new smLoginFailedMessage("Lobby is already full");
                String text = gson.toJson(message, smLoginFailedMessage.class);
                handler.sendMessage(text);
            }

            else {
                lobby.put(handlerId, handler);
                smPongPort ms = new smPongPort(handlerId);
                handler.sendMessage(gson.toJson(ms, smPongPort.class));
                if (handlerId == 1) {
                    smAskNickname message = new smAskNickname("\nPlease select nickname");
                    String text = gson.toJson(message, smAskNickname.class);
                    handler.sendMessage(text);
                } else if (lobby.size() > (clientHandlers.size() + 1)) {
                    smNotify message = new smNotify("\nWaiting for player to choose nickname...");
                    String text = gson.toJson(message, smNotify.class);
                    handler.sendMessage(text);
                } else if (!controller.gameIsSet()) {
                    smNotify message = new smNotify("\nWaiting for host player to set game status...");
                    String text = gson.toJson(message, smNotify.class);
                    handler.sendMessage(text);
                } else {
                    askNickname(false, handler);
                }

                handler.start();
            }
        }
    }

    public void addToGame(String nick, ClientHandler clientHandler) {
        ServerMessage message;
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
        StringBuilder nicks = new StringBuilder();
        for (String nickname : clientHandlers.keySet()) {
            if (nicks.length() == 0) {
                nicks.append(nickname);
            } else
                nicks.append(nicks + ", " + nickname);
        }
        return nicks.toString();
    }

    public Controller getController() {
        return controller;
    }

    public void removeFromLobby(int id) {
        lobby.remove(id);
    }

    public void removeClientHandler(ClientHandler handler) {
        for(String nick: clientHandlers.keySet()){
            if(clientHandlers.get(nick).equals(handler)){
                clientHandlers.remove(nick);
                break;
            }
        }

        if (clientHandlers.keySet().isEmpty()) {
            System.out.println("Ready for a new game");
            lobby = new HashMap<>();
            clientHandlers = new HashMap<>();
            controller = new Controller(this);
            maxPlayers = 4;
        }
    }


    public void setGameStatus(String mode, int numOfPlayers, ClientHandler clientHandler){
        maxPlayers = numOfPlayers;
        controller.setGameStatus(mode, numOfPlayers);

        if (numOfPlayers < lobby.size())
            for (int i = (numOfPlayers + 1); i <= lobby.size(); i++) {
                smNotify message = new smNotify("\nYou can't join the game because you are player number " + i + " out of " + numOfPlayers +
                        ".\nYou will be disconnected");
                String text = gson.toJson(message, smNotify.class);
                lobby.get(i).sendMessage(text);

                removeFromLobby(i);
            }

        else if (lobby.size() >= (clientHandlers.size() + 1)) {
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
        String text = gson.toJson(message, smAskNickname.class);
        if(nextPlayer)
            lobby.get(clientHandler.getHandlerId() + 1).sendMessage(text);
        else
            clientHandler.sendMessage(text);
    }

    public String getNickByHandlerId(int id) {
        for (String nick : clientHandlers.keySet())
            if (clientHandlers.get(nick).equals(lobby.get(id)))
                return nick;

        return null;
    }

    public void manageDisconnection(int handlerId) {
        if (clientHandlers.size() != maxPlayers)
            nameUnknownDisconnection(handlerId);
        else
            nameKnownDisconnection(handlerId);
    }

    private void nameUnknownDisconnection(int id) {
        String text = "\nThe game will be closed because the connection with player " + id + " is lost.";

        smCloseThemAll message = new smCloseThemAll(text);
        text = gson.toJson(message, smCloseThemAll.class);

        communicateDisconnection(id, text);

        if (clientHandlers.size() <= id)
            System.out.println("\nThe connection whit player " + getNickByHandlerId(id) + " is lost. The game is finished.");
        else
            System.out.println("\nThe connection whit player " + id + " is lost. The game is finished.");
    }

    private void communicateDisconnection(int id, String message) {
        for(int player: lobby.keySet())
            if(player != id)
                lobby.get(player).sendMessage(message);
    }

    private void nameKnownDisconnection(int id) {
        String text = "\nThe game will be closed because the connection with player " + getNickByHandlerId(id) + " is lost.";

        smCloseThemAll message = new smCloseThemAll(text);
        text = gson.toJson(message, smCloseThemAll.class);

        sendAllExceptPlayer(getNickByHandlerId(id), text);

        System.out.println("\nThe connection whit player " + getNickByHandlerId(id) + " is lost. The game is finished.");
    }
}
