package it.polimi.ingsw.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Pong extends  Thread{
    private ServerSocket serverSocket;
    private PrintStream printStream = null;
    private Socket socket;
    private BufferedReader bufferedReader =null;
    private boolean connected;
    private PingTimer pingTimer;
    private ClientHandler clientHandler;

    public  Pong(ClientHandler handler){
        clientHandler = handler;
        try {
            serverSocket = new ServerSocket(777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server is up");

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected= true;

    }

    @Override
    public void run() {
        String ping = null;
        String pong = "pong";
        pingTimer = new PingTimer(this);
        pingTimer.start();
        while(connected){
            try {
                ping = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("client is down");
                System.exit(-1);
            }
            if(ping.equals("ping")){
                System.out.println("ping received");
                pingTimer.resetTimer();
                printStream.println(pong);
                System.out.println("pong sent");
            }
            else{
                connected = false;
            }
        }

    }

    public  void closePong(){
        clientHandler.closeClientConnection();
    }
}
