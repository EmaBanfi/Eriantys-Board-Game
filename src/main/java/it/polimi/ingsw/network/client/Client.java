package it.polimi.ingsw.network.client;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmTestConnection;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.ServerGson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private Socket socket;
    private DataOutputStream dos;
    private BufferedReader br;
    private final BufferedReader kb;
    private String str;
    private final ServerGson smgson;
    private final View view;
    private final String ip;

    public Client(View view, String ip) {
        this.view = view;

        this.ip = ip;
        connection();

        smgson = new ServerGson();
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        kb = new BufferedReader(new InputStreamReader(System.in));
    }

    public void connection() {
        try {
            socket = new Socket(ip, 888);
        } catch (IOException e) {
            System.out.println("Server not found, the executable will be closed");
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                str = br.readLine();
            } catch (IOException e) {
                System.out.println("\nThe connection with the server is lost.\nThe game will be closed.");

                System.exit(-1);
            }
            if(str != null) {
                ServerMessage message = smgson.deserialize(str);
                message.processMessage(this);
            }

            else {
                try {
                    if (br.read() == -1) {
                        System.out.println("\nThe connection with the server is lost.\nThe game will be closed.");

                        System.exit(-1);
                    }
                } catch (IOException e) {
                    System.out.println("\nThe connection with the server is lost.\nThe game will be closed.");

                    System.exit(-1);
                }
            }

        }
    }

    public void closeConnection() {
        try {
            dos.close();
            br.close();
            kb.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        try {
            dos.writeBytes(text + "\n");
        } catch (IOException e) {
            System.out.println("\nThe connection with the server is lost.\nThe game will be closed.");

            System.exit(-1);
        }
    }

    public  void CloseClient(){
        System.out.println("Connection is down");
        System.exit(-1);
    }

    public View getView() {
        return view;
    }

    public void startPing(int port){
        Ping ping = new Ping(ip, this, port);
    }
}
