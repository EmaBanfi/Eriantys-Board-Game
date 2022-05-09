package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.cmNickname;
import it.polimi.ingsw.network.messages.clientMessages.cmSetGameStatus;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.SmGson;
import it.polimi.ingsw.network.messages.serverMessages.smClientFirstMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {

    Socket s;
    DataOutputStream dos;
    BufferedReader br;
    BufferedReader kb;
    String str, str1;
    boolean serverUp;
    SmGson smgson;

    public Client() {
        connection();

        smgson = new SmGson();
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
                str1 = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (str1.equals("exit"))
                serverUp = false;
            else {
                ServerMessage message = smgson.deserialize(str1);
                message.processMessage(this);
            }

        }

        closeConnection();

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

    public void askNickname() {
        try {
            str = kb.readLine();
            Gson gson = new Gson();
            cmNickname message = new cmNickname(str);
            String text = gson.toJson(message, cmNickname.class);
            dos.writeBytes(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
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
        cmSetGameStatus message = new cmSetGameStatus(numOfPlayers, mode);
        String text = gson.toJson(message, cmSetGameStatus.class);
        try {
            dos.writeBytes(text + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client();
    }
    
}

