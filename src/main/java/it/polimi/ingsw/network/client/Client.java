package it.polimi.ingsw.network.client;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmSetGameStatus;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.ServerGson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket s;
    private DataOutputStream dos;
    private BufferedReader br;
    private BufferedReader kb;
    private String str;
    private boolean serverUp;
    private ServerGson smgson;
    private View view;


    public Client(String viewType) {
        if(viewType.equals("1"))
            view = new CLI(this);
        else
            view = new GUI();

        connection();

        smgson = new ServerGson();
        try {
            dos = new DataOutputStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        kb = new BufferedReader(new InputStreamReader(System.in));
        serverUp = true;

        receive();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Do you want to play with CLI or GUI?");
        System.out.println("CLI: 1");
        System.out.println("GUI: 2");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        String viewType;
        do {
            viewType = br.readLine();
            if (!(viewType.equals("1")) & !(viewType.equals("2")))
                System.out.println("Please digit 1 or 2");
        } while (!(viewType.equals("1")) & !(viewType.equals("2")));

        Client client = new Client(viewType);
    }

    public void connection() {
        try {
            s = new Socket("192.168.3.80", 888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while (serverUp) {
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(str != null) {
                System.out.println("received from server: "+ str);
                ServerMessage message = smgson.deserialize(str);
                message.processMessage(this);
            }
        }
    }

    public void closeConnection() {
        try {
            dos.close();
            br.close();
            kb.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        try {
            dos.writeBytes(text + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public View getView() {
        return view;
    }
}
