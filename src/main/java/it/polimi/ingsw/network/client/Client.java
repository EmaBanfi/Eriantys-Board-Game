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
    private BufferedReader kb;
    private String str;
    private boolean serverUp;
    private ServerGson smgson;
    private View view;
    private int timeout = 15000;


    public Client(String viewType) {
        if(viewType.equals("1"))
            view = new CLI(this);
        else
            view = new GUI();

        connection();

        smgson = new ServerGson();
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        kb = new BufferedReader(new InputStreamReader(System.in));
        serverUp = true;

        receive();
    }

    public static void main(String[] args) throws Exception {
        Scanner input;
        input = new Scanner(System.in);

        System.out.println("\nDo you want to play with CLI or GUI?");
        System.out.println("CLI: 1");
        System.out.println("GUI: 2");

        String viewType;
        do {
            viewType = input.nextLine();
            if (!(viewType.equals("1")) & !(viewType.equals("2")))
                System.out.println("Please digit 1 or 2");
        } while (!(viewType.equals("1")) & !(viewType.equals("2")));

        new Client(viewType);
    }

    public void connection() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nInsert the server IP address");
        String ip = scanner.nextLine();

        try {
            socket = new Socket(ip, 888);
        } catch (IOException e) {
            System.out.println("Server not found, the executable will be closed");
            System.exit(-1);
        }
    }

    public synchronized void receive() {
        while (serverUp) {
            try {
                str = br.readLine();
            } catch (IOException e) {
                System.out.println("\nThe connection with the server is lost.\nThe game will be closed.");

                System.exit(-1);
            }
            if(str != null) {
                //System.out.println("received from server: "+ str);
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

    private synchronized void testConnection() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        cmTestConnection message = new cmTestConnection();
        String text =  gson.toJson(message, cmTestConnection.class);
        send(text);
    }

    public View getView() {
        return view;
    }
}
