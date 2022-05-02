package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class Client {

    Socket s;
    DataOutputStream dos;
    BufferedReader br;
    BufferedReader kb;
    String str, str1;
    boolean serverUp;

    public Client(){
        connection();

        try {
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
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


    public void send() {
        try {
            if ((str = kb.readLine()).equals("exit"))
                closeConnection();
            else {
                dos.writeBytes(str + "\n");
            }
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
                Gson gson = new Gson();
                Message message = gson.fromJson(str1, Message.class);
                message.processMessage(this);
            }

        }

        closeConnection();

    }

    public void closeConnection() {
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


