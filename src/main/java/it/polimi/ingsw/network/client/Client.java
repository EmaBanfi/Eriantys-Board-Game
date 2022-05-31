package it.polimi.ingsw.network.client;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.SetGameStatus;
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


    public Client() {
        view = new CLI(this);

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

    public Client(GUI gui) {
        view = gui;

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

        if (viewType.equals("1")) {
            Client client = new Client();
        }
        else {
            String[] strings = new String[0];
            GUI.main(strings);
        }
    }

    public void connection() {
        try {
            s = new Socket("localhost", 888);
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

    public void askGameStatus() {
        // questa parte va sostituita con input da GUI
        int numOfPlayers = 0;
        String mode = null;
        try {
            do {
                numOfPlayers = Integer.parseInt(kb.readLine());
            } while (numOfPlayers < 2 || numOfPlayers > 4);

            do {
                mode = kb.readLine();
            } while (!mode.equals("expert") && !mode.equals("normal"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        SetGameStatus message = new SetGameStatus(numOfPlayers, mode);
        String text = gson.toJson(message, SetGameStatus.class);
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
