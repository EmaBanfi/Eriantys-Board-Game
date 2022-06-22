package it.polimi.ingsw.network.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ping extends Thread{
    private Socket socket;
    private DataOutputStream dos;
    private BufferedReader br;

    private  final Client client;
    private  final PongTimer pongTimer;

    private boolean connected;

    public Ping(String ip, Client client, int port){
        this.client=client;
        try {
            socket = new Socket(ip, 777+port);
        } catch (IOException e) {
            System.out.println("Server not found, the executable will be closed");
            System.exit(-1);
        }

        try {
            dos = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pongTimer = new PongTimer(this);
        this.start();

    }

    @Override
    public void run(){
        String ping = "ping";
        String pong = null;
        connected = true;
        try {
            dos.writeBytes(ping+"\n");
        } catch (IOException e) {
            System.out.println("server is down");
            System.exit(-1);
        }
        //System.out.println("ping sent");
        pongTimer.start();
        while (connected){
            try {
                pong=br.readLine();
            } catch (IOException e) {
                System.out.println("server is down");
                System.exit(-1);
            }
            if (pong.equals("pong")) {
                System.out.println("pong received");
                pongTimer.resetTimer();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dos.writeBytes(ping + "\n");
                } catch (IOException e) {
                    System.out.println("server is down");
                    System.exit(-1);
                }
                System.out.println("ping sent");
            }
        }

    }

    public void closePing(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = false;
        client.CloseClient();
    }
}
